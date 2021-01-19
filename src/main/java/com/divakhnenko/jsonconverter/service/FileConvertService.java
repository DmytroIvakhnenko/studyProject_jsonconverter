package com.divakhnenko.jsonconverter.service;

import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;

public interface FileConvertService {
    Path getJsonFile(MultipartFile multipartFile);
    void deleteTmpFile(Path file);
}
