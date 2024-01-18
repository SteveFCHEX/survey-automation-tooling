package com.currenthealth.sas.controllers;

import com.currenthealth.sas.model.payload.TypeformSurveyConverterTemplatePayload;
import com.currenthealth.sas.model.typeform.forms.TypeformForm;
import com.currenthealth.sas.services.TemplatePayloadService;
import com.currenthealth.sas.services.TypeformService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

/**
 * API to create a template for the fact converter service
 */
@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("template")
public class TemplateCreatorController {

    private final TypeformService typeformService;

    private final TemplatePayloadService templatePayloadService;

    /**
     * Returns the vat data set for all vat items
     *
     * @return the converter template payload
     */
    @Operation(
            description = "Gets a users the current user has visibility of",
            security = {@SecurityRequirement(name = "bearer-key")}
    )
    @GetMapping(path = "/{surveyId}")
    ResponseEntity<TypeformSurveyConverterTemplatePayload> generatePayload(@PathVariable("surveyId") String surveyId) {

        TypeformSurveyConverterTemplatePayload payload = TypeformSurveyConverterTemplatePayload.builder().build();

        Optional<TypeformForm> typeformSurvey = typeformService.getFormBySurveyId(surveyId);

        if (typeformSurvey.isPresent()) {
            payload = templatePayloadService.createPayload(typeformSurvey.get());
        }

        return ResponseEntity.ok(payload);

    }
}
