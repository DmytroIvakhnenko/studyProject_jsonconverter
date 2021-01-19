package com.divakhnenko.jsonconverter.controller;

import com.divakhnenko.jsonconverter.service.FileConvertService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;

@Controller
@RequiredArgsConstructor
public class FileController {
    private final FileConvertService fileConvertService;

    @PostMapping("/upload")
    public HttpEntity<byte[]> createFile(@RequestParam("file") MultipartFile file) throws IOException {
        var jsonFile = fileConvertService.getJsonFile(file);
        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.parseMediaType(file.getContentType()));
        header.set(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=" + jsonFile.getFileName().toString().replace(" ", "_"));
        header.setContentLength(Files.size(jsonFile));
        var httpEntity = new HttpEntity<>(Files.readAllBytes(jsonFile), header);
        fileConvertService.deleteTmpFile(jsonFile);
        return httpEntity;
    }

    @GetMapping("/")
    public String homeView(Model model) {
        return "index";
    }
}
