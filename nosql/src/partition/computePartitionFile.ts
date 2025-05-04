import { FilePath } from "../types/type";
import { computeIndex } from "./computeIndex.js";
import { join } from "path";
type ComputePartitionFile = {
  key?: string;
  file: FilePath;
};
export const computePartitionFile = ({
  key = "id",
  file,
}: ComputePartitionFile) => {
  const index = computeIndex(key);
  return join("mock_data", "partition", `${index}`, file) as FilePath;
};
