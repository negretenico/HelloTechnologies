package com.hellotechnologies.realtionalDatabase.service.file;

import com.hellotechnologies.realtionalDatabase.models.result.Result;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class FileReadService {
    public Result<File> read(String path) {
        try {
            return Result.success(new File(path));
        } catch (Exception e) {
            return Result.failure(e.getLocalizedMessage());
        }
    }
}
