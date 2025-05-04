import { NUM_PARTITIONS } from "./constants.js";
import { murmur3 } from "murmurhash-js";
//TODO: today we hardcode the number of partitions but this should likely come from the client
export const computeIndex = (partitionKey: string) => {
  const hash = murmur3(partitionKey, 0);
  return hash % NUM_PARTITIONS;
};
