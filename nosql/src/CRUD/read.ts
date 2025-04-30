import {FilePath} from "../types/type";
import {readFileSync} from "fs";

export const read = (path: FilePath) =>{
    try{
        return readFileSync(path,{encoding:'utf8'});
    }catch (e){
        console.error(e);
    }
}