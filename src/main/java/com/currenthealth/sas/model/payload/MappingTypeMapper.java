package com.currenthealth.sas.model.payload;

import java.util.HashMap;
import java.util.Map;

public class MappingTypeMapper {

    private static final Map<String, MappingType> MAPPINGS = new HashMap<>();

    static {
        MAPPINGS.put("yes_no", MappingType.LABEL);
        MAPPINGS.put("opinion_scale", MappingType.PARSED);
        MAPPINGS.put("number", MappingType.PARSED);
        MAPPINGS.put("multiple_choice", MappingType.LABEL);
    }

    public static MappingType getMappingType(String key) {
        return MAPPINGS.getOrDefault(key, null);
    }
}

