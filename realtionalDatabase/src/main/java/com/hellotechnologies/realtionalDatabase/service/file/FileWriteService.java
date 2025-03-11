package com.hellotechnologies.realtionalDatabase.service.file;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hellotechnologies.realtionalDatabase.models.result.ResultStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;

@Service
public class FileWriteService<T> {
    private static final Logger log = LoggerFactory.getLogger(FileWriteService.class);

    private final ObjectMapper objectMapper;

    public FileWriteService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public synchronized ResultStatus writeToFile(String fileName, T content) {
        try (FileWriter fileWriter = new FileWriter(fileName)) {
            String valueAsString = objectMapper.writeValueAsString(content);
            fileWriter.write(valueAsString);
            return ResultStatus.SUCCESS;
        } catch (IOException e) {
            log.error("We could not write to the file because we encountered error {}", e.getLocalizedMessage());
            return ResultStatus.FAILURE;
        }
    }
}
