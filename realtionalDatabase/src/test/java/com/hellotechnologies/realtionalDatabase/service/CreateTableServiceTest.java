package com.hellotechnologies.realtionalDatabase.service;

import com.hellotechnologies.realtionalDatabase.service.create.CreateTableService;
import com.hellotechnologies.realtionalDatabase.service.file.CreateFileService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;

@ExtendWith(MockitoExtension.class)
class CreateTableServiceTest {
    CreateTableService createTableService;
    @Mock
    CreateFileService createFileService;

    @BeforeEach
    void setUp() {
        createTableService = new CreateTableService(createFileService);
    }

    @Test
    void givenAProperTableNameThenIShouldSeeSuccess() throws Exception {
        Mockito.when(createFileService.createFile(Mockito.anyString())).thenReturn(true);
        Assertions.assertTrue(createTableService.createTable("ThisIsATable").isSuccess());
    }

    @Test
    void givenAProperTableNameThenIShouldSeeTrueAsData() throws Exception {
        Mockito.when(createFileService.createFile(Mockito.anyString())).thenReturn(true);
        Assertions.assertTrue(createTableService.createTable("ThisIsATable").getData());
    }

    @Test
    void givenAnImproperTableNameThenIShouldSeeFailure() throws Exception {
        Assertions.assertTrue(createTableService.createTable("").isFailure());
    }

    @Test
    void givenFileServiceCannotCreateFilThenIShouldSeeFailure() throws Exception {
        Mockito.when(createFileService.createFile(Mockito.anyString())).thenThrow(IOException.class);
        Assertions.assertTrue(createTableService.createTable("ThisIsATable").isFailure());
    }
}