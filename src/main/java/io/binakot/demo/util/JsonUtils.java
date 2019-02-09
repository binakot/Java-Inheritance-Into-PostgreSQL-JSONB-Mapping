package io.binakot.demo.util;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public enum JsonUtils {;

    public static final ObjectMapper JSON_MAPPER;
    static {
        JSON_MAPPER = new ObjectMapper();
        JSON_MAPPER.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        JSON_MAPPER.disable(MapperFeature.DEFAULT_VIEW_INCLUSION);
    }

    public static final class JsonColumn { }
}
