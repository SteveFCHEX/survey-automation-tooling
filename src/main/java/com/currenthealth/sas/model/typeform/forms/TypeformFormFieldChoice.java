package com.currenthealth.sas.model.typeform.forms;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;

@Builder
@JsonIgnoreProperties
public record TypeformFormFieldChoice(
        String ref,
        String label) {
}
