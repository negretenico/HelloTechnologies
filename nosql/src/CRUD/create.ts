import {FilePath, OperationStatus} from "../types/type";
import {writeFile} from "fs";

export const create = (path:FilePath):OperationStatus =>{
    try{
        writeFile(path,JSON.stringify({}),()=>{})
        return 'SUCCESS'
    }catch (e){
        return 'ERROR'
    }
}