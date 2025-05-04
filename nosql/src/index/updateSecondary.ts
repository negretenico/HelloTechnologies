import { join } from "path";
import { read } from "../CRUD/read.js";
import { update } from "../CRUD/update.js";
import { FilePath } from "../types/type.js";
type UpdateSecondary = {
  table: string;
  field: string;
  val: string;
  key: string;
};
export const updateSecondary = ({
  table,
  field,
  val,
  key,
}: UpdateSecondary) => {
  const filePath = join("index", table, `${field}.json`) as FilePath;
  const original = read(filePath);
  if (!original) {
    return "ERROR";
  }
  const index: Record<string, string[]> = JSON.parse(original);
  if (index[val]) {
    index[val].push(key);
  } else {
    index[val] = [key];
  }
  update(filePath, index);
  return "SUCCESS";
};
