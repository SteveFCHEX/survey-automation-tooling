package com.currenthealth.sas.model.payload;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.util.List;

@Builder
public record TypeformSurveyConverterTemplatePayload(String surveyId,
                                                     String description,
                                                     @ArraySchema(
                schema = @Schema(ref = "TypeformSurveyFieldMapping")
        ) List<TypeformSurveyFieldMapping> typeformFieldMappings) {
}
