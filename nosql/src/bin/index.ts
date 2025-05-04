#!/usr/bin/env node
import { program } from "commander";
import { isFilePath } from "../types/checker.js";
import { read } from "../CRUD/read.js";
import { create } from "../CRUD/create.js";
import { update } from "../CRUD/update.js";
import { del } from "../CRUD/delete.js";
import { parseObject } from "../checker/parseObject.js";
import { computePartitionFile } from "../partition/computePartitionFile.js";
import { logToFile } from "../logging/logToFile.js";
console.log("Starting CLI application...");
program
  .name("HelloTechnologies-NoSql")
  .description(
    "CLI application to be an implementation of a NoSql DB like DynamoDB to improve understanding"
  )
  .version("0.0.1");
program
  .command("read")
  .argument("<fileName>", "Path to the JSON file")
  .option("-k, --key <string>")
  .action((fileName, options) => {
    if (!isFilePath(fileName)) {
      console.error(
        `❌ Invalid file path: '${fileName}'. Must end with '.json'.`
      );
      process.exit(1); // Exit with failure
    }
    const partitionedFilePath = computePartitionFile({
      key: options.key,
      file: fileName,
    });
    const data = read(partitionedFilePath);
    logToFile({
      operation: "READ",
      table: fileName,
    });
    console.log(`📄 Contents of ${fileName}:\n`, data);
  });

program
  .command("delete")
  .argument("<fileName>", "Path to the JSON file")
  .option("-k, --key <string>")
  .action((fileName, options) => {
    if (!isFilePath(fileName)) {
      console.error(
        `❌ Invalid file path: '${fileName}'. Must end with '.json'.`
      );
      process.exit(1); // Exit with failure
    }
    const partitionedFilePath = computePartitionFile({
      key: options.key,
      file: fileName,
    });
    const status = del(partitionedFilePath);
    if (status === "ERROR") {
      console.error(`Could not delete file ${fileName}`);
      process.exit(1);
    }
    logToFile({
      operation: "DELETE",
      table: fileName,
    });
    console.log(`Deleted file ${fileName}`);
  });

program
  .command("write")
  .argument("<fileName>", "File to create")
  .option(
    "-d, --data <json>",
    "Optional JSON data to insert into the initial file"
  )
  .option("-k, --key <string>")
  .action((fileName, options) => {
    if (!isFilePath(fileName)) {
      console.error(
        `❌ Invalid file path: '${fileName}'. Must end with '.json'.`
      );
      process.exit(1); // Exit with failure
    }
    const parsedDataOrErrMsg = parseObject(options?.data);
    if (typeof parsedDataOrErrMsg === "string") {
      console.error(`❌ Error processing ${parsedDataOrErrMsg}`);
      process.exit(1);
    }

    const fieldName = options.key ?? "id";
    const partitionValue = parsedDataOrErrMsg[fieldName];
    if (typeof partitionValue !== "string") {
      console.error(
        `❌ Partition key '${fieldName}' must be a string in your data.`
      );
      process.exit(1);
    }
    const partitionedFilePath = computePartitionFile({
      key: partitionValue,
      file: fileName,
    });
    logToFile({
      operation: "WRITE",
      table: fileName,
      data: parsedDataOrErrMsg,
    });
    const status = create(partitionedFilePath, parsedDataOrErrMsg);
    if (status === "ERROR") {
      console.error(`❌ Error creating ${fileName}`);
      process.exit(1);
    }
    console.log(`✅Successfully created file=${fileName}`);
  });

program
  .command("update")
  .argument("<fileName>", "File to create")
  .option(
    "-d, --data <json>",
    "Optional JSON data to insert into the initial file"
  )
  .option("-k, --key <string>")
  .action((fileName, options) => {
    if (!isFilePath(fileName)) {
      console.error(
        `❌ Invalid file path: '${fileName}'. Must end with '.json'.`
      );
      process.exit(1); // Exit with failure
    }
    const parsedDataOrErrMsg = parseObject(options?.data);
    if (typeof parsedDataOrErrMsg === "string") {
      console.error(`❌ Error processing ${parsedDataOrErrMsg}`);
      process.exit(1);
    }
    const fieldName = options.key ?? "id";
    const partitionValue = parsedDataOrErrMsg[fieldName];
    if (typeof partitionValue !== "string") {
      console.error(
        `❌ Partition key '${fieldName}' must be a string in your data.`
      );
      process.exit(1);
    }
    const partitionedFilePath = computePartitionFile({
      key: partitionValue,
      file: fileName,
    });
    logToFile({
      operation: "UPDATE",
      table: fileName,
      data: parsedDataOrErrMsg,
    });
    const status = update(partitionedFilePath, parsedDataOrErrMsg);
    if (status === "ERROR") {
      console.error(`❌ Error updating ${fileName}`);
      process.exit(1);
    }
    console.log(
      `✅Successfully updated file=${fileName} with data=${JSON.stringify(
        options.data
      )}`
    );
  });

program.parse(process.argv);
