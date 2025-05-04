import { FilePath } from "./type";

export const isFilePath = (val: unknown): val is FilePath => {
  return typeof val === "string" && val.endsWith(".json");
};
export const isPlainObject = (val: unknown): val is Record<string, unknown> => {
  return typeof val === "object" && val !== null && !Array.isArray(val);
};
