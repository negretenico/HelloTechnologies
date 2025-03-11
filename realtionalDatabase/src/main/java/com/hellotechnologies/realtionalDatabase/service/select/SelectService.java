package com.hellotechnologies.realtionalDatabase.service.select;

import com.hellotechnologies.realtionalDatabase.models.result.Result;
import com.hellotechnologies.realtionalDatabase.models.row.RowDefinition;
import com.hellotechnologies.realtionalDatabase.service.file.FileReadService;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.SerializationUtils;
import org.yaml.snakeyaml.util.Tuple;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class SelectService {
    FileReadService fileReadService;

    public SelectService(FileReadService fileReadService) {
        this.fileReadService = fileReadService;
    }

    public Result<List<Object>> select(String tableName, List<String> fieldNames) throws IOException {
        Path path = Path.of("data", "table", tableName + ".table");
        Result<File> f = fileReadService.read(path.toString());
        if (f.isFailure()) {
            return Result.failure(f.getErrMsg());
        }
        RowDefinition row = (RowDefinition) SerializationUtils.deserialize(FileUtils.readFileToByteArray(f.getData()));
        Set<String> columnNameSet = row.row().stream().map(s -> s._1().name()).collect(Collectors.toSet());
        if (!columnNameSet.containsAll(fieldNames)) {
            return Result.failure("We got a column name that does not match");
        }

        return Result.success(row.row().stream()
                .filter(tuple -> fieldNames.contains(tuple._1().name()))
                .map(Tuple::_2)
                .toList());
    }
}

