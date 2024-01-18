package com.currenthealth.sas.model.payload;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Data
@Slf4j
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL) // This annotation ensures that null fields are not serialized
public class TypeformSurveyFieldMapping {

    private String typeformBlockId;

    private String factName;

    private DataType dataType;

    private MappingType mappingType;

    private List<LabelValuePair> labelValuePairs;

}
