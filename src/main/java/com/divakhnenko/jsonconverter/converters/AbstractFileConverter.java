package com.divakhnenko.jsonconverter.converters;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;


@RequiredArgsConstructor
@Slf4j
public abstract class AbstractFileConverter implements Converter<InputStream, List<Map<String, String>>> {
    private final String delimiter;

    public List<Map<String, String>> convert(InputStream inputStream) {
        List<Map<String, String>> content = new ArrayList<>();
        List<String> header;

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            if (reader.ready()) {
                header = splitLine(reader.readLine());

                while (reader.ready()) {
                    List<String> line = splitLine(reader.readLine());
                    Map<String, String> object = new LinkedHashMap<>(header.size());
                    for (int i = 0; i < header.size(); i++) {
                        object.put(header.get(i), line.get(i));
                    }
                    content.add(object);
                }
            }
        } catch (IOException e) {
            log.error("Exception happened during file conversion {}", e);
        }
        return content;
    }

    public List<String> splitLine(String line) {
        if (Objects.nonNull(line) && !line.isBlank()) {
            return Arrays.asList(line.split(delimiter));
        } else {
            return Collections.emptyList();
        }
    }
}
