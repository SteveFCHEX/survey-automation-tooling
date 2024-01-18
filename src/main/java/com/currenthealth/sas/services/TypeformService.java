package com.currenthealth.sas.services;

import com.currenthealth.sas.model.typeform.forms.TypeformForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class TypeformService {
    // Can't get this to work with tests, hardcoded bc we would have to put the api key in a config file anyway

    @Value("${typeform.key}")
    private String typeformKey;

    @Value("${typeform.baseurl}")
    private String TYPEFORM_BASE_URL;

    @Value("${typeform.formsurl}")
    private String TYPEFORM_FORMS_ENDPOINT;

    public Optional<TypeformForm> getFormBySurveyId(String surveyId) {
        String singleFormEndpoint = TYPEFORM_FORMS_ENDPOINT + surveyId;

        WebClient client = WebClient
                .builder()
                .baseUrl(TYPEFORM_BASE_URL)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .build();

        return client
                .get()
                .uri(singleFormEndpoint)
                .headers(h -> h.setBearerAuth(typeformKey))
                .retrieve()
                .bodyToMono(TypeformForm.class)
                .onErrorResume(WebClientResponseException.NotFound.class, e -> Mono.empty()) // Handle 404 exception specifically
                .blockOptional();
    }
}
