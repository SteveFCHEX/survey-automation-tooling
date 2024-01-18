package com.currenthealth.sas.services;

import com.currenthealth.sas.model.payload.*;
import com.currenthealth.sas.model.typeform.forms.TypeformForm;
import com.currenthealth.sas.model.typeform.forms.TypeformFormFieldChoice;
import com.currenthealth.sas.model.typeform.forms.TypeformFormQuestion;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.currenthealth.sas.model.payload.DataTypeMapper.getDataType;
import static com.currenthealth.sas.model.payload.MappingTypeMapper.getMappingType;

/**
 * Class handles the conversion of typeform surveys
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class TemplatePayloadService {

    /**
     * Method will create a template payload based on the questions in the typeform object.
     *
     * @param typeformForm object containing the survey questions
     * @return the converter template payload
     */
    public TypeformSurveyConverterTemplatePayload createPayload(TypeformForm typeformForm) {

        //filter questions we don't handle yet
        List<TypeformFormQuestion> validTypeformFields = filterQuestionTypes(typeformForm.fields());

        //convert typeform questions to field mappings
        List<TypeformSurveyFieldMapping> typeformFieldMappings = parseQuestions(typeformForm.id(), validTypeformFields);

        //build the converter template payload
        TypeformSurveyConverterTemplatePayload payload = TypeformSurveyConverterTemplatePayload
                .builder()
                .surveyId(typeformForm.id())
                .description(typeformForm.title())
                .typeformFieldMappings(typeformFieldMappings)
                .build();

        return payload;
    }

    /**
     * Method will parse a list of typeform survey questions and return a list of field mappings
     *
     * @param surveyId typeform survey Id
     * @param typeformFormQuestions list of typeform questions
     * @return field mappings for these questions
     */
    private List<TypeformSurveyFieldMapping> parseQuestions(String surveyId, List<TypeformFormQuestion> typeformFormQuestions) {

        List<TypeformSurveyFieldMapping> typeformFieldMappings = new ArrayList<>();

        int questionIndex = 1;
        for (TypeformFormQuestion field : typeformFormQuestions) {

            List<LabelValuePair> labelValuePairs = new ArrayList<>();

            parseLabelValuePairs(field, labelValuePairs);

            parseYesNoQuestions(field, labelValuePairs);

            //build the mapping
            TypeformSurveyFieldMapping typeformSurveyFieldMapping = TypeformSurveyFieldMapping.builder()
                    .typeformBlockId(field.ref())
                    .factName("SURVEY_" + surveyId.toUpperCase() + "_Q" + questionIndex++)
                    .dataType(getDataType(field.type()))
                    .mappingType(getMappingType(field.type()))
                    .labelValuePairs(labelValuePairs)
                    .build();

            typeformFieldMappings.add(typeformSurveyFieldMapping);
        }

        return typeformFieldMappings;
    }


    /**
     * This method filters out any invalid question types. i.e. Statements.
     *
     * @param fields list to field
     * @return a list of valid questions
     */
    private List<TypeformFormQuestion> filterQuestionTypes(List<TypeformFormQuestion> fields) {

        return fields.stream()
                .filter(formQuestion -> DataTypeMapper.MAPPINGS.containsKey(formQuestion.type()))
                .collect(Collectors.toList());
    }

    /**
     * If the question is of type yes_no then we add yes/no label pairs.
     *
     * @param field typeform survey question
     * @param labelValuePairs the list to append
     */
    private void parseYesNoQuestions(TypeformFormQuestion field, List<LabelValuePair> labelValuePairs) {
        if (field.type().equals("yes_no")) {

            LabelValuePair labelValuePairYes = LabelValuePair.builder().label("Yes").value("1").build();
            LabelValuePair labelValuePairNo = LabelValuePair.builder().label("No").value("0").build();

            labelValuePairs.add(labelValuePairYes);
            labelValuePairs.add(labelValuePairNo);
        }
    }

    /**
     * This method checks if the question has properties and if it does, then it will add them to the mappings as
     * label value pairs.
     *
     * @param field typeform survey question
     * @param labelValuePairs the list to append
     */
    private void parseLabelValuePairs(TypeformFormQuestion field, List<LabelValuePair> labelValuePairs) {
        int x = 1;

        if (field.properties() != null
                && !field.properties().isEmpty()
                && field.properties().get(0).choices() != null) {
            for (TypeformFormFieldChoice typeformFormFieldChoice : field.properties().get(0).choices()) {

                LabelValuePair labelValuePair = LabelValuePair
                        .builder()
                        .label(typeformFormFieldChoice.label())
                        .value("" + x++)
                        .build();

                labelValuePairs.add(labelValuePair);
            }
        }
    }
}
