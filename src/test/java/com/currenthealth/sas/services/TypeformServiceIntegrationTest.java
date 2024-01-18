package com.currenthealth.sas.services;

import com.currenthealth.sas.model.typeform.forms.TypeformForm;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@RequiredArgsConstructor
class TypeformServiceIntegrationTest {

    @Autowired
    private TypeformService typeformService;

    @Value("${typeform.baseurl}")
    protected static String TYPEFORM_BASE_URL;

    /**
     * Tests a successful callout to typeform returns a typeform object we are expecting.
     */
    @Test
    void getFormBySurveyId_RealCall() {
        String surveyId = "ai3SCG8Q"; // Replace with a valid survey ID for testing
        String surveyTitle = "UCLA Thoracic: Incentive Spirometer";
        Optional<TypeformForm> result = typeformService.getFormBySurveyId(surveyId);

        assertTrue(result.isPresent());
        assertEquals(result.get().id(), surveyId);
        assertEquals(result.get().title(), surveyTitle);
        assertEquals(2, result.get().fields().size());
    }

    /**
     * Tests an unsuccessful callout to typeform passing an empty survey Id.
     */
    @Test
    void getFormBySurveyId_RealCal_empty() {
        String surveyId = "";

        Optional<TypeformForm> result = typeformService.getFormBySurveyId(surveyId);

        assertFalse(result.isPresent());
    }

    /**
     * Tests an unsuccessful callout to typeform passing an survey Id that doesn't exist.
     */
    @Test
    void getFormBySurveyId_RealCal_notfound() {
        String surveyId = "AAAAAAAA";

        Optional<TypeformForm> result = typeformService.getFormBySurveyId(surveyId);

        assertFalse(result.isPresent());
    }

}
