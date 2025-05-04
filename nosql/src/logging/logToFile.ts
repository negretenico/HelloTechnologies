import { appendFile } from "fs";
import { join } from "path";
type Operation = "READ" | "WRITE" | "UPDATE" | "DELETE";
type LogToFile = {
  operation: Operation;
  table: string;
  data?: any;
};
export const logToFile = ({ operation, table, data }: LogToFile) => {
  const timestamp = new Date().toISOString();
  const filePath = join("logs", `${table}.log`);
  const logMessage = JSON.stringify({
    ts: timestamp,
    ...(data ? { data } : {}),
    operation,
    table,
  });
  const formattedMessage = `[${timestamp}] ${logMessage}\n`;

  appendFile(filePath, formattedMessage, (err) => {
    if (err) {
      console.error("Error writing to log file:", err);
    }
  });
};
