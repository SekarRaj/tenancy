package com.saas.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import static com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS;

public class JsonUtils {
    private static final ObjectMapper mapper = new ObjectMapper()
            .enable(SerializationFeature.INDENT_OUTPUT)
            .disable(WRITE_DATES_AS_TIMESTAMPS)
            .registerModule(new JavaTimeModule());

    public static String convertToJson(Object obj) {
        try {
            return mapper.writeValueAsString(obj);
        } catch (Exception ex) {
            System.err.println(ex.getLocalizedMessage());
            throw new RuntimeException("Cannot convert to JSON - " + ex.getLocalizedMessage());
        }
    }
}
