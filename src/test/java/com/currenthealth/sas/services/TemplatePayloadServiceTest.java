package com.currenthealth.sas.services;

import com.currenthealth.sas.model.payload.TypeformSurveyConverterTemplatePayload;
import com.currenthealth.sas.model.typeform.forms.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


class TemplatePayloadServiceTest {

    private TemplatePayloadService service;

    @BeforeEach
    void setUp() {
        service = new TemplatePayloadService();
    }

    /**
     * Tests the createPayload method with a mock TypeformForm containing valid questions.
     * Ensures that the payload is correctly generated based on the input form.
     */
    @Test
    void createPayload_WithValidData_ReturnsCorrectPayload() {
        // Arrange
        TypeformForm form = createMockTypeformFormWithQuestions();

        // Act
        TypeformSurveyConverterTemplatePayload payload = service.createPayload(form);

        // Assert
        assertNotNull(payload);
        assertEquals(form.id(), payload.surveyId());
        assertFalse(payload.typeformFieldMappings().isEmpty());
    }

    /**
     * Tests the createPayload method with a mock TypeformForm containing valid questions.
     * Ensures that the payload is correctly generated based on the input form.
     */
    @Test
    void createPayload_WithValidAllQuestionTypesData_ReturnsCorrectPayload() {
        // Arrange
        TypeformForm form = createMockTypeformFormWithMultiQuestions();

        // Act
        TypeformSurveyConverterTemplatePayload payload = service.createPayload(form);

        // Assert
        assertNotNull(payload);
        assertEquals(form.id(), payload.surveyId());
        assertFalse(payload.typeformFieldMappings().isEmpty());
    }

    /**
     * Tests the createPayload method with an empty list of questions.
     * This test verifies that the service handles scenarios with no questions correctly,
     * ensuring that an empty payload is returned.
     */
    @Test
    void createPayload_WithNoQuestions_ReturnsEmptyPayload() {
        // Arrange
        TypeformForm form = TypeformForm.builder()
                .id("testId")
                .title("testTitle")
                .language("en")
                .fields(List.of()) // empty list of questions
                .build();

        // Act
        TypeformSurveyConverterTemplatePayload payload = service.createPayload(form);

        // Assert
        assertTrue(payload.typeformFieldMappings().isEmpty());
    }

    /**
     * Helper method to create a mock TypeformForm object with predefined questions.
     * This mock data is used to test the createPayload method of the TemplatePayloadService.
     */
    private TypeformForm createMockTypeformFormWithQuestions() {
        // Create a mock TypeformForm object with test data
        TypeformFormQuestion question1 = TypeformFormQuestion.builder()
                .ref("q1")
                .title("Question 1")
                .type("yes_no")
                .build();

        // Add more questions as needed
        List<TypeformFormQuestion> questions = Collections.singletonList(question1);

        return TypeformForm.builder()
                .id("mockId")
                .title("mockTitle")
                .language("en")
                .fields(questions)
                .build();
    }

    /**
     * Helper method to create a mock TypeformForm object with predefined questions.
     * This mock data is used to test the createPayload method of the TemplatePayloadService.
     * This mock includes multi choice questions
     */
    private TypeformForm createMockTypeformFormWithMultiQuestions() {
        // Create a mock TypeformForm object with test data
        TypeformFormQuestion question1 = TypeformFormQuestion.builder()
                .ref("q1")
                .title("Question 1")
                .type("yes_no")
                .build();


        TypeformFormFieldChoice choice1 = TypeformFormFieldChoice.builder().label("Fewer than 10 times per day").ref("1").build();
        TypeformFormFieldChoice choice2 = TypeformFormFieldChoice.builder().label("Between 10-20 times per day").ref("2").build();
        TypeformFormFieldChoice choice3 = TypeformFormFieldChoice.builder().label("More than 20 times per day").ref("3").build();

        List<TypeformFormFieldChoice> choiceList = new ArrayList<>();
        choiceList.add(choice1);
        choiceList.add(choice2);
        choiceList.add(choice3);

        TypeformFormFieldProperties properties = TypeformFormFieldProperties.builder().choices(choiceList).build();
        List<TypeformFormFieldProperties> propertiesList = new ArrayList<>();
        propertiesList.add(properties);

        TypeformFormQuestion question2 = TypeformFormQuestion.builder()
                .ref("q2")
                .title("Question 2")
                .type("multiple_choice")
                .properties(propertiesList)
                .build();

        TypeformFormQuestion question3 = TypeformFormQuestion.builder()
                .ref("q3")
                .title("Question 3")
                .type("statement")
                .build();

        TypeformFormQuestion question4 = TypeformFormQuestion.builder()
                .ref("q4")
                .title("Question 4")
                .type("opinion_scale")
                .build();

        TypeformFormQuestion question5 = TypeformFormQuestion.builder()
                .ref("q5")
                .title("Question 5")
                .type("number")
                .build();


        // Add more questions as needed
        List<TypeformFormQuestion> questions = Arrays.asList(question1, question2, question3, question4, question5);

        return TypeformForm.builder()
                .id("mockId")
                .title("mockTitle")
                .language("en")
                .fields(questions)
                .build();
    }
}
