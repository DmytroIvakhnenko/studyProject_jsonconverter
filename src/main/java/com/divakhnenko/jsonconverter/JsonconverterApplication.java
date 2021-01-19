package com.divakhnenko.jsonconverter;

import com.divakhnenko.jsonconverter.converters.Converter;
import com.divakhnenko.jsonconverter.converters.CsvConverter;
import com.divakhnenko.jsonconverter.converters.TxtConverter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootApplication
public class JsonconverterApplication {
    @Bean
    public Converter<InputStream, List<Map<String, String>>> csvConverter(){
        return new CsvConverter();
    }

    @Bean
    public Converter<InputStream, List<Map<String, String>>> txtConverter(){
        return new TxtConverter();
    }

    @Bean(name="converterMap")
    public Map<String, Converter<InputStream, List<Map<String, String>>>> converterMyMap (){
        Map<String, Converter<InputStream, List<Map<String, String>>>> converterMap = new HashMap<>();
        converterMap.put("csv", csvConverter());
        converterMap.put("txt", txtConverter());
        return converterMap;
    }

    public static void main(String[] args) {
        SpringApplication.run(JsonconverterApplication.class, args);
    }

}
