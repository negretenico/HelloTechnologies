import { create } from "../CRUD/create.js";
import { FilePath } from "../types/type.js";
import { join } from "path";

type CreateSecondaryIndex = {
  table: string;
  field: string;
};
export const createSecondaryIndex = ({
  table,
  field,
}: CreateSecondaryIndex) => {
  const filePath = join("index", table, `${field}.json`) as FilePath;
  create(filePath);
};
