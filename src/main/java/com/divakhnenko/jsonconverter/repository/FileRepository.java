package com.divakhnenko.jsonconverter.repository;

import java.nio.file.Path;

public interface FileRepository {
    Path createFile(String name);

    void deleteFile(Path file);
}
