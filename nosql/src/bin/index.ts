#!/usr/bin/env node
import { program } from "commander";
import { isFilePath } from "../types/checker.js";
import { read } from "../CRUD/read.js";
import { create } from "../CRUD/create.js";
import { update } from "../CRUD/update.js";
import { del } from "../CRUD/delete.js";
import { parseObject } from "../checker/parseObject.js";
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
  .action((fileName, options) => {
    if (!isFilePath(fileName)) {
      console.error(
        `❌ Invalid file path: '${fileName}'. Must end with '.json'.`
      );
      process.exit(1); // Exit with failure
    }
    const data = read(fileName);
    console.log(`📄 Contents of ${fileName}:\n`, data);
  });

program
  .command("delete")
  .argument("<fileName>", "Path to the JSON file")
  .action((fileName, options) => {
    if (!isFilePath(fileName)) {
      console.error(
        `❌ Invalid file path: '${fileName}'. Must end with '.json'.`
      );
      process.exit(1); // Exit with failure
    }
    const status = del(fileName);
    if (status === "ERROR") {
      console.error(`Could not delete file ${fileName}`);
      process.exit(1);
    }
    console.log(`Deleted file ${fileName}`);
  });

program
  .command("write")
  .argument("<fileName>", "File to create")
  .option(
    "-d, --data <json>",
    "Optional JSON data to insert into the initial file"
  )
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
    const status = create(fileName, parsedDataOrErrMsg);
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
    const status = update(fileName, parsedDataOrErrMsg);
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
