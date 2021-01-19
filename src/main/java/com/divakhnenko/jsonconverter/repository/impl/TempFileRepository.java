package com.divakhnenko.jsonconverter.repository.impl;

import com.divakhnenko.jsonconverter.repository.FileRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
@Slf4j
public class TempFileRepository implements FileRepository {
    private static final String TEMP_DIR = System.getProperty("java.io.tmpdir");

    @Override
    public Path createFile(String name) {
        Path tempFile = Paths.get(TEMP_DIR + name);
        try {
            Files.createFile(tempFile);
        } catch (IOException e) {
            log.error("Exception happened during temp file creation {}", e);
        }
        return tempFile;
    }

    @Override
    public void deleteFile(Path file) {
        try {
            Files.deleteIfExists(file);
        } catch (IOException e) {
            log.error("Exception happened during temp file deletion {}", e);
        }
    }

}
