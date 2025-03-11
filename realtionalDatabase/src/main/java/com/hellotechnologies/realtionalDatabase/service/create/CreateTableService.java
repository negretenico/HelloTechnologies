package com.hellotechnologies.realtionalDatabase.service.create;

import com.hellotechnologies.realtionalDatabase.models.result.Result;
import com.hellotechnologies.realtionalDatabase.service.file.CreateFileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Path;

@Service
public class CreateTableService {
    private static final Logger log = LoggerFactory.getLogger(CreateTableService.class);
    private final CreateFileService createFileService;

    public CreateTableService(CreateFileService createFileService) {
        this.createFileService = createFileService;

    }

    /**
     * Writes a file to disk representing the table data, this will reutrn a result of true if and only if the
     * table is CREATED, if the file already exists on disk we log an error and return false denoting an existent table
     *
     * @param name name of the file who we want to write to disk
     * @return a {@link Result} containg true or false if the file was written to disk
     * @throws Exception - if given an improper name for the file
     */
    public Result<Boolean> createTable(String name) {
        if (!isValidName(name)) {
            return Result.failure(String.format("The string %s is not a valid table name", name));
        }
        try {
            boolean hasFile = createFileService.createFile(Path.of("data", "table", String.join("", name, ".table")).toString());
            log.info("Table created successfully: {}", name);
            return Result.success(hasFile);
        } catch (IOException e) {
            log.error("Failed to create table {}: {}", name, e.getMessage());
            return Result.failure(e.getLocalizedMessage());
        }
    }

    private boolean isValidName(String name) {
        return name != null && !name.isBlank() && name.matches("[a-zA-Z0-9_-]+");
    }

}
