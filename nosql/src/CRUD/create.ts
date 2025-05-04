import { FilePath, OperationStatus } from "../types/type";
import { writeFile, existsSync, mkdirSync } from "fs";
import { dirname } from "path";

export const create = (path: FilePath, data?: unknown): OperationStatus => {
  const dirPath = dirname(path);

  if (!existsSync(dirPath)) {
    mkdirSync(dirPath, { recursive: true });
  }
  try {
    writeFile(path, JSON.stringify(data ?? {}), () => {});
    return "SUCCESS";
  } catch (e) {
    return "ERROR";
  }
};
