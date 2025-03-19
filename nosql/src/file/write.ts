import {FilePath} from "../types/type";
import fs from "fs";
export const writeToFile = <T>(file:FilePath,data:T) =>{
    fs.writeFile(file,data)
}