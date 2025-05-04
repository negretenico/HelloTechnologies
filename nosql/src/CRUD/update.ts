import { FilePath, OperationStatus } from "../types/type";
import { writeFileSync } from "fs";
import { read } from "../CRUD/read.js";

export const update = <T>(path: FilePath, data: T): OperationStatus => {
  let existingData = read(path);

  if (existingData === undefined) {
    console.error("❌ Could not read or parse existing file.");
    return "ERROR";
  }

  const updatedData = {
    ...JSON.parse(existingData),
    ...data,
  };

  try {
    writeFileSync(path, JSON.stringify(updatedData, null, 2)); // Pretty print with 2-space indent
    return "SUCCESS";
  } catch (err) {
    console.error("❌ Failed to write updated data:", err);
    return "ERROR";
  }
};
