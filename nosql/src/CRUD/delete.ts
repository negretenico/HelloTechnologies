import {FilePath, OperationStatus} from "../types/type";
import {unlinkSync} from "fs";
//delete is not allowed as a names
export const del = (path:FilePath):OperationStatus=>{
    try{
        unlinkSync(path);
        return 'SUCCESS'
    }catch (e){
        return 'ERROR'
    }
}