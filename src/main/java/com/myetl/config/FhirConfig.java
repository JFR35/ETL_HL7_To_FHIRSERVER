package com.myetl.config;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.rest.client.api.IGenericClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuración de FHIR para la integración con Aidbox.
 *
 * <p>Esta clase inicializa el contexto de FHIR y el cliente para interactuar
 * con el servidor FHIR</p>
 */
@Configuration
public class FhirConfig {

    /**
     * URL base del servidor FHIR (Aidbox).
     *
     * <p>Esta propiedad se obtiene de la configuración externa en properties.</p>
     */
    @Value("${aidbox.fhir.base-url}")
    private String baseUrl;

    /**
     * Declarar un bean para FhirContext.
     */
    @Bean
    public FhirContext fhirContext() {
        return FhirContext.forR4();
    }

    /**
     * Declara un bean para IGenericClient.
     * Spring inyectará por paraámetro automáticamente el FhirContext declarado arriba.
     *
     * @param fhirContext El FhirContext inyectado por Spring.
     * @param baseUrl La URL base del servidor FHIR inyectada desde properties.
     * @return Una instancia de IGenericClient.
     */
    @Bean
    public IGenericClient fhirClient(@Value("${aidbox.fhir.base-url}") String baseUrl, FhirContext fhirContext) {
        return fhirContext.newRestfulGenericClient(baseUrl);
    }
}
