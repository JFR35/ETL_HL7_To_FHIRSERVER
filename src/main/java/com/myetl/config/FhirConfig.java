package com.myetl.config;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.rest.client.api.IGenericClient;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;


@RequiredArgsConstructor
@Getter
@Configuration
public class FhirConfig {

    @Value("${aidbox.fhir.base-url}")
    private String baseUrl;

    private FhirContext fhirContext;
    private IGenericClient client;

    @PostConstruct
    public void init() {
        this.fhirContext = FhirContext.forR4();
        this.client = fhirContext.newRestfulGenericClient(baseUrl);
    }
}
