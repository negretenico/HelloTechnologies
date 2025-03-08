package com.hellotechnologies.realtionalDatabase.service.file;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

@Service
public class CreateFileService {
    private static final Logger log = LoggerFactory.getLogger(CreateFileService.class);

    public boolean createFile(String filePath) throws IOException {
        File file = new File(filePath);
        File parentDir = file.getParentFile();
        if (parentDir != null && !parentDir.exists()) {
            if (!parentDir.mkdirs()) {
                log.error("Failed to create parent directories for: {}", filePath);
                return false;
            }
        }
        return file.createNewFile();
    }
}