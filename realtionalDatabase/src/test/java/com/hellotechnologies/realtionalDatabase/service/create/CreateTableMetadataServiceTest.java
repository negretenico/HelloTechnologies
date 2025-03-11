package com.hellotechnologies.realtionalDatabase.service.create;

import com.hellotechnologies.realtionalDatabase.models.TableDefinition;
import com.hellotechnologies.realtionalDatabase.models.result.ResultStatus;
import com.hellotechnologies.realtionalDatabase.service.file.CreateFileService;
import com.hellotechnologies.realtionalDatabase.service.file.FileWriteService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;

@ExtendWith(MockitoExtension.class)
class CreateTableMetadataServiceTest {
    @Mock
    FileWriteService fileWriteService;
    @Mock
    CreateFileService createFileService;
    TableDefinition tableDefinition = new TableDefinition("SomeName", null);
    CreateTableMetadataService createTableMetadataService;

    @BeforeEach
    void setUp() {
        createTableMetadataService = new CreateTableMetadataService(createFileService, fileWriteService);
    }

    @Test
    void givenICanProperlyCreateTheFileAndICanWriteToItThenIShouldSeeASuccess() throws IOException {
        Mockito.when(createFileService.createFile(Mockito.anyString())).thenReturn(true);
        Mockito.when(fileWriteService.writeToFile(Mockito.anyString(), Mockito.any())).thenReturn(ResultStatus.SUCCESS);
        Assertions.assertTrue(createTableMetadataService.createTableMetadata(tableDefinition).isSuccess());
    }

    @Test
    void givenICanProperlyCreateTheFileAndICanNotWriteToItThenIShouldSeeAFailure() throws IOException {
        Mockito.when(createFileService.createFile(Mockito.anyString())).thenReturn(true);
        Mockito.when(fileWriteService.writeToFile(Mockito.anyString(), Mockito.any())).thenReturn(ResultStatus.FAILURE);
        Assertions.assertTrue(createTableMetadataService.createTableMetadata(tableDefinition).isFailure());
    }

    @Test
    void givenICannotCreateThenIShouldGetAFailure() throws IOException {
        Mockito.when(createFileService.createFile(Mockito.anyString())).thenThrow(IOException.class);
        Assertions.assertTrue(createTableMetadataService.createTableMetadata(tableDefinition).isFailure());
    }
}