import {FilePath, OperationStatus} from "../types/type";
import {appendFileSync} from "fs";

export const update = <T>(path:FilePath, data:T):OperationStatus=>{
    try{
        appendFileSync(path, JSON.stringify(data), "utf8");
        return  'SUCCESS'
    }catch (e){
        return 'ERROR'
    }
}