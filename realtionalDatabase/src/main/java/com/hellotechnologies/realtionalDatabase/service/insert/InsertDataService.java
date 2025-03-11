package com.hellotechnologies.realtionalDatabase.service.insert;

import com.hellotechnologies.realtionalDatabase.models.result.Result;
import com.hellotechnologies.realtionalDatabase.models.row.RowDefinition;
import com.hellotechnologies.realtionalDatabase.service.validation.SchemaValidationService;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.SerializationUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Objects;

@Service
public class InsertDataService {
    SchemaValidationService schemaValidationService;

    InsertDataService(SchemaValidationService schemaValidationService) {
        this.schemaValidationService = schemaValidationService;
    }

    public Result<String> insert(String tableName, RowDefinition data) {
        Path path = Path.of("data", "table", String.join("", tableName, ".table"));
        Result<Boolean> validation = schemaValidationService.validate(data, tableName);
        if (validation.isFailure() || !validation.getData()) {
            return Result.failure("Data is invalid");
        }
        byte[] out = SerializationUtils.serialize(data);
        if (Objects.isNull(out)) {
            return Result.failure("DATA cannot be null");
        }
        try {
            FileUtils.writeByteArrayToFile(new File(path.toString()), out);
            return Result.success("Foo");
        } catch (IOException e) {
            return Result.failure("We could not serialize the data");
        }
    }
}
