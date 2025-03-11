package com.hellotechnologies.realtionalDatabase.service.validation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hellotechnologies.realtionalDatabase.models.TableDefinition;
import com.hellotechnologies.realtionalDatabase.models.result.Result;
import com.hellotechnologies.realtionalDatabase.models.row.RowDefinition;
import com.hellotechnologies.realtionalDatabase.service.file.FileReadService;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

@Service
public class SchemaValidationService {
    FileReadService fileReadService;
    ObjectMapper objectMapper;

    public SchemaValidationService(FileReadService fileReadService, ObjectMapper objectMapper) {
        this.fileReadService = fileReadService;
        this.objectMapper = objectMapper;
    }

    public Result<Boolean> validate(RowDefinition rowDefinition, String tableName) {
        Path path = Path.of("data", "metadata", String.join("", tableName, ".metadata"));
        Result<File> fileResult = fileReadService.read(path.toString());
        if (fileResult.isFailure()) {
            return Result.failure("Could not locate metadata for table: " + tableName);
        }
        try {
            TableDefinition tableDefinition = objectMapper.readValue(fileResult.getData(), TableDefinition.class);
            return Result.success(tableDefinition.row().equals(rowDefinition));
        } catch (IOException e) {
            return Result.failure(e.getLocalizedMessage());
        }
    }
}
