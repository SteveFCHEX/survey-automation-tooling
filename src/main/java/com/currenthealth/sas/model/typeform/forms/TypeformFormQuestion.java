package com.currenthealth.sas.model.typeform.forms;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;

import java.util.List;

@Builder
@JsonIgnoreProperties
public record TypeformFormQuestion(
        String ref,

        String title,
        String type,
        @JsonFormat(with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY) List<
                TypeformFormFieldProperties> properties) {
}
