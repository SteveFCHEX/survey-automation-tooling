package com.currenthealth.sas.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

import com.currenthealth.sas.model.payload.TypeformSurveyConverterTemplatePayload;
import com.currenthealth.sas.model.typeform.forms.TypeformForm;
import com.currenthealth.sas.services.TemplatePayloadService;
import com.currenthealth.sas.services.TypeformService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import java.util.Optional;

public class TemplateCreatorControllerTest {

    private TypeformService mockTypeformService;
    private TemplatePayloadService mockTemplatePayloadService;
    private TemplateCreatorController controller;

    @BeforeEach
    public void setUp() {
        mockTypeformService = mock(TypeformService.class);
        mockTemplatePayloadService = mock(TemplatePayloadService.class);
        controller = new TemplateCreatorController(mockTypeformService, mockTemplatePayloadService);
    }

    /**
     * Test the generatePayload method when the TypeformService returns a present TypeformForm.
     * It should call the createPayload method of the TemplatePayloadService and return the expected payload.
     */
    @Test
    public void testGetCompanies_WithPresentTypeformForm() {
        // Set up
        String surveyId = "testSurveyId";
        TypeformForm mockForm = TypeformForm.builder().build(); // Populate with test data as needed
        TypeformSurveyConverterTemplatePayload mockPayload = TypeformSurveyConverterTemplatePayload.builder().build(); // Populate with test data as needed

        when(mockTypeformService.getFormBySurveyId(surveyId)).thenReturn(Optional.of(mockForm));
        when(mockTemplatePayloadService.createPayload(mockForm)).thenReturn(mockPayload);

        // Execute
        ResponseEntity<TypeformSurveyConverterTemplatePayload> response = controller.generatePayload(surveyId);

        // Assert
        assertEquals(mockPayload, response.getBody());
        verify(mockTemplatePayloadService, times(1)).createPayload(mockForm);
    }

    /**
     * Test the generatePayload method when the TypeformService returns an empty Optional.
     * It should not call the createPayload method of the TemplatePayloadService and return an empty payload.
     */
    @Test
    public void testGetCompanies_WithEmptyTypeformForm() {
        // Set up
        String surveyId = "testSurveyId";
        when(mockTypeformService.getFormBySurveyId(surveyId)).thenReturn(Optional.empty());

        // Execute
        ResponseEntity<TypeformSurveyConverterTemplatePayload> response = controller.generatePayload(surveyId);

        // Assert
        assertNull(response.getBody().surveyId());
        assertNull(response.getBody().description());
        assertNull(response.getBody().typeformFieldMappings());
        verify(mockTemplatePayloadService, never()).createPayload(any());
    }
}
