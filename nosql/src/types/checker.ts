import {FilePath} from "./type";

export const isFilePath = (val:unknown): val is FilePath =>{
    return typeof val==="string" && val.endsWith(".json")
}