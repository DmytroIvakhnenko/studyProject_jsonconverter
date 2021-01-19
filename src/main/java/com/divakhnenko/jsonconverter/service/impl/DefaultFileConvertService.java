package com.divakhnenko.jsonconverter.service.impl;

import com.divakhnenko.jsonconverter.converters.Converter;
import com.divakhnenko.jsonconverter.repository.FileRepository;
import com.divakhnenko.jsonconverter.service.FileConvertService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class DefaultFileConvertService implements FileConvertService {
    @Resource(name="converterMap")
    private Map<String, Converter<InputStream, List<Map<String, String>>>> converterMyMap;
    private final FileRepository fileRepository;

    @Override
    public Path getJsonFile(MultipartFile multipartFile) {
        List<Map<String, String>> content = Collections.emptyList();

        var converter = converterMyMap.computeIfAbsent(getExtension(multipartFile.getOriginalFilename()), key -> {
            throw new RuntimeException("Converter for extension " + key + " not found");
        });
        try {
            content = converter.convert(multipartFile.getInputStream());
        } catch (IOException e) {
            log.error("Exception happened while extracting inputstream from file {}", e);
        }
        Path file = fileRepository.createFile(multipartFile.getName());
        try (BufferedWriter writer = Files.newBufferedWriter(file)) {
            Gson gson = new GsonBuilder().create();
            gson.toJson(content, writer);
        } catch (IOException e) {
            log.error("Exception happened during Json file creation {}", e);
        }
        return file;
    }

    @Override
    public void deleteTmpFile(Path file) {
        fileRepository.deleteFile(file);
    }

    private String getExtension(String fileName) {
        return FilenameUtils.getExtension(fileName);
    }
}
