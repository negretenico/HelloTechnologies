package com.hellotechnologies.realtionalDatabase.service.insert;

import com.hellotechnologies.realtionalDatabase.models.result.Result;
import com.hellotechnologies.realtionalDatabase.models.row.RowDefinition;
import com.hellotechnologies.realtionalDatabase.service.validation.SchemaValidationService;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.util.SerializationUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class InsertDataServiceTest {
    @Mock
    SchemaValidationService schemaValidationService;

    @Mock
    FileUtils fileUtils;
    InsertDataService insertDataService;

    private final String tableName = "myTable";
    private final RowDefinition rowDefinition = new RowDefinition(List.of());
    private final Path expectedPath = Path.of("data", "table", "myTable.table");

    @BeforeEach
    void setup() {
        insertDataService = new InsertDataService(schemaValidationService);
    }

    @Test
    void whenValidationSucceeds_andSerializationSucceeds_thenReturnSuccess() throws IOException {
        Mockito.when(schemaValidationService.validate(rowDefinition, tableName))
                .thenReturn(Result.success(true));

        try (MockedStatic<SerializationUtils> serializationUtils = Mockito.mockStatic(SerializationUtils.class);
             MockedStatic<FileUtils> fileUtilsMock = Mockito.mockStatic(FileUtils.class)) {

            byte[] serializedData = new byte[]{1, 2, 3};
            serializationUtils.when(() -> SerializationUtils.serialize(rowDefinition))
                    .thenReturn(serializedData);


            Result<String> result = insertDataService.insert(tableName, rowDefinition);

            Assertions.assertTrue(result.isSuccess());
            Assertions.assertEquals("Foo", result.getData());

            // Verify FileUtils was called with correct parameters
            fileUtilsMock.verify(() ->
                    FileUtils.writeByteArrayToFile(
                            Mockito.argThat(file -> file.getPath().equals(expectedPath.toString())),
                            Mockito.eq(serializedData)
                    )
            );
        }
    }

    @Test
    void whenValidationFails_thenReturnFailure() {
        Mockito.when(schemaValidationService.validate(rowDefinition, tableName))
                .thenReturn(Result.failure("Data is invalid"));


        Result<String> result = insertDataService.insert(tableName, rowDefinition);

        // Then
        Assertions.assertTrue(result.isFailure());
        Assertions.assertEquals("Data is invalid", result.getErrMsg());

        // Verify that no serialization is attempted
        try (MockedStatic<SerializationUtils> serializationUtils = Mockito.mockStatic(SerializationUtils.class)) {
            serializationUtils.verifyNoInteractions();
        }
    }

    @Test
    void whenValidationReturnsSuccess_butValidationDataIsFalse_thenReturnFailure() {
        Mockito.when(schemaValidationService.validate(rowDefinition, tableName))
                .thenReturn(Result.success(false));


        Result<String> result = insertDataService.insert(tableName, rowDefinition);
        Assertions.assertTrue(result.isFailure());
        Assertions.assertEquals("Data is invalid", result.getErrMsg());
    }

    @Test
    void whenSerializationReturnsNull_thenReturnFailure() {
        Mockito.when(schemaValidationService.validate(rowDefinition, tableName))
                .thenReturn(Result.success(true));

        try (MockedStatic<SerializationUtils> serializationUtils = Mockito.mockStatic(SerializationUtils.class)) {
            serializationUtils.when(() -> SerializationUtils.serialize(rowDefinition))
                    .thenReturn(null);


            Result<String> result = insertDataService.insert(tableName, rowDefinition);

            Assertions.assertTrue(result.isFailure());
            Assertions.assertEquals("DATA cannot be null", result.getErrMsg());
        }
    }

    @Test
    void whenFileWriteThrowsIOException_thenReturnFailure() throws IOException {
        Mockito.when(schemaValidationService.validate(rowDefinition, tableName))
                .thenReturn(Result.success(true));

        try (MockedStatic<SerializationUtils> serializationUtils = Mockito.mockStatic(SerializationUtils.class);
             MockedStatic<FileUtils> fileUtilsMock = Mockito.mockStatic(FileUtils.class)) {

            byte[] serializedData = new byte[]{1, 2, 3};
            serializationUtils.when(() -> SerializationUtils.serialize(rowDefinition))
                    .thenReturn(serializedData);

            fileUtilsMock.when(() ->
                    FileUtils.writeByteArrayToFile(
                            Mockito.any(File.class),
                            Mockito.eq(serializedData)
                    )
            ).thenThrow(new IOException("Test IO Exception"));


            Result<String> result = insertDataService.insert(tableName, rowDefinition);

            Assertions.assertTrue(result.isFailure());
            Assertions.assertEquals("We could not serialize the data", result.getErrMsg());
        }
    }
}