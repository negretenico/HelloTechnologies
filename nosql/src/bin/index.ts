
import {program} from "commander";
import {isFilePath} from "../types/checker.js";
import {read} from "../CRUD/read.js";
console.log('Starting CLI application...');
program
    .name('HelloTechnologies-NoSql')
    .description('CLI application to be an implementation of a NoSql DB like DynamoDB to improve understanding')
    .version('0.0.1');
program
    .command('read')
    .argument('<fileName>', 'Path to the JSON file')
    .action((fileName,options)=>{
        if (!isFilePath(fileName)) {
            console.error(`❌ Invalid file path: '${fileName}'. Must end with '.json'.`);
            process.exit(1); // Exit with failure
        }
        const data = read(fileName);
        console.log(`📄 Contents of ${fileName}:\n`, data);
    });

program.parse(process.argv);
