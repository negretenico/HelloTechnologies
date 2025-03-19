import fs from "fs";
import {FilePath} from "../types/type";
export const append_to_file =<T> (file:FilePath,data: T) =>{
    fs.appendFile(file,data);
}