package com.hellotechnologies.realtionalDatabase.service.file;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@ExtendWith(MockitoExtension.class)
class CreateFileServiceTest {

    @TempDir
    Path tempDir;

    private CreateFileService createFileService;

    @BeforeEach
    void setUp() {
        createFileService = new CreateFileService();
    }

    @Test
    void givenExistingDirectoryShouldCreateFile() throws IOException {
        String fileName = "test.table";
        Path filePath = tempDir.resolve(fileName);
        boolean result = createFileService.createFile(filePath.toString());

        Assertions.assertTrue(result);
        Assertions.assertTrue(Files.exists(filePath));
    }

    @Test
    void givenNonExistingDirectoriesShouldCreateDirectoriesAndFile() throws IOException {
        String nestedPath = "nested/directories/test.table";
        Path filePath = tempDir.resolve(nestedPath);
        boolean result = createFileService.createFile(filePath.toString());
        Assertions.assertTrue(result);
        Assertions.assertTrue(Files.exists(filePath));
        Assertions.assertTrue(Files.exists(filePath.getParent()));
    }

    @Test
    void givenDeepNestedDirectoriesShouldCreateAllDirectories() throws IOException {
        String deepPath = "level1/level2/level3/level4/level5/test.table";
        Path filePath = tempDir.resolve(deepPath);

        boolean result = createFileService.createFile(filePath.toString());

        Assertions.assertTrue(result);
        Assertions.assertTrue(Files.exists(filePath));
        Assertions.assertTrue(Files.exists(filePath.getParent()));
    }

    @Test
    void givenExistingFileShouldReturnFalse() throws IOException {
        String fileName = "existing.table";
        Path filePath = tempDir.resolve(fileName);
        Files.createFile(filePath);
        Assertions.assertTrue(Files.exists(filePath));

        boolean result = createFileService.createFile(filePath.toString());

        Assertions.assertFalse(result);
        Assertions.assertTrue(Files.exists(filePath));
    }

    @Test
    void givenInvalidPathShouldThrowException() {
        String invalidPath = "\0invalid";
        Assertions.assertThrows(IOException.class, () -> {
            createFileService.createFile(invalidPath);
        });
    }
}