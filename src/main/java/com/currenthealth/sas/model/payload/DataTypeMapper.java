package com.currenthealth.sas.model.payload;

import java.util.Map;
import java.util.HashMap;

public class DataTypeMapper {
    public static final Map<String, DataType> MAPPINGS = new HashMap<>();

    static {
        MAPPINGS.put("yes_no", DataType.DOUBLE);
        MAPPINGS.put("opinion_scale", DataType.DOUBLE);
        MAPPINGS.put("number", DataType.DOUBLE);
        MAPPINGS.put("multiple_choice", DataType.STRING);
    }

    public static DataType getDataType(String key) {
        return MAPPINGS.getOrDefault(key, null);
    }
}

