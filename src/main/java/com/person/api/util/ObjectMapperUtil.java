package com.person.api.util;


import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.person.api.exception.JsonConvertException;
import com.person.api.exception.MessageErrorImpl;

import java.io.IOException;
import java.util.TimeZone;

public abstract class ObjectMapperUtil {

    private static ObjectMapper MAPPER = getMapper();

    private ObjectMapperUtil() {
    }

    public static <T> T toObject(final String json, Class<T> clazz) {
        try {
            return MAPPER.readValue(json, clazz);
        } catch (IOException e) {
            throw new JsonConvertException(MessageErrorImpl.JSON_TO_OBJECT_FAIL);
        }
    }

    public static String toJson(final Object json) {

        try {
            return MAPPER.writeValueAsString(json);
        } catch (IOException e) {
            throw new JsonConvertException(MessageErrorImpl.OBJECT_TO_JSON_FAIL);
        }
    }

    public static ObjectMapper getMapper() {

        ObjectMapper mapper = new ObjectMapper();
        JavaTimeModule javaTimeModule = new JavaTimeModule();
        mapper.registerModule(javaTimeModule);
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES).configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
        mapper.setTimeZone(TimeZone.getDefault());

        return mapper;
    }


}
