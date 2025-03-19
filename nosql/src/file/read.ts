import {FilePath} from "../types/type";
import fs from "fs";

export const readFile =  (path:FilePath) =>{
    return fs.readFile(path);
}