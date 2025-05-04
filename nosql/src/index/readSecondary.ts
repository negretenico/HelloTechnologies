import { read } from "../CRUD/read.js";
import { join } from "path";
import { FilePath } from "../types/type.js";
type ReadSecondary = {
  table: string;
  field: string;
  query: string;
};
export const readSecondary = ({ table, field, query }: ReadSecondary) => {
  const filePath = join("index", table, field, ".json") as FilePath;
  const fileContent = read(filePath);
  if (fileContent === undefined) {
    return "ERROR";
  }

  return JSON.parse(fileContent)[query];
};
