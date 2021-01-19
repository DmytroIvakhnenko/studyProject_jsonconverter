package com.divakhnenko.jsonconverter.converters;

public interface Converter <I, O> {
    O convert(I input);
}
