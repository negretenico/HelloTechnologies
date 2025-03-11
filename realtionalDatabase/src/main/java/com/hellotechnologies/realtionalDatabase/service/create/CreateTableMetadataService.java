package com.hellotechnologies.realtionalDatabase.service.create;

import com.hellotechnologies.realtionalDatabase.models.TableDefinition;
import com.hellotechnologies.realtionalDatabase.models.result.Result;
import com.hellotechnologies.realtionalDatabase.models.result.ResultStatus;
import com.hellotechnologies.realtionalDatabase.service.file.CreateFileService;
import com.hellotechnologies.realtionalDatabase.service.file.FileWriteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Path;

@Service
public class CreateTableMetadataService {
    private static final Logger log = LoggerFactory.getLogger(CreateTableMetadataService.class);
    private final CreateFileService createFileService;
    private final FileWriteService<TableDefinition> fileWriteService;

    public CreateTableMetadataService(CreateFileService createFileService, FileWriteService<TableDefinition> fileWriteService) {
        this.createFileService = createFileService;
        this.fileWriteService = fileWriteService;
    }

    public Result<Boolean> createTableMetadata(TableDefinition tableDefinition) {
        Path filePath = Path.of("data", "metadata", String.join("", tableDefinition.name(), ".metadata"));
        try {
            createFileService.createFile(filePath.toString());
        } catch (IOException e) {
            return Result.failure(e.getLocalizedMessage());
        }
        ResultStatus resultStatus = fileWriteService.writeToFile(filePath.toString(), tableDefinition);
        if (resultStatus == ResultStatus.FAILURE) {
            return Result.failure("Could not write to file");
        }
        return Result.success(true);
    }
}
