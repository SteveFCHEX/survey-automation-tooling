package com.currenthealth.sas.model.typeform.forms;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@JsonIgnoreProperties
public record TypeformForm(
        String id,
        String title,
        String language,
        @JsonFormat(with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY) List<TypeformFormQuestion> fields) {
}
