package com.myetl.config;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.rest.client.api.IGenericClient;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * Configuración de FHIR para la integración con Aidbox.
 *
 * <p>Esta clase inicializa el contexto de FHIR y el cliente para interactuar
 * con el servidor FHIR especificado en la configuración.</p>
 */
@RequiredArgsConstructor
@Getter
@Configuration
public class FhirConfig {

    /**
     * URL base del servidor FHIR (Aidbox).
     *
     * <p>Esta propiedad se obtiene de la configuración externa en properties.</p>
     */
    @Value("${aidbox.fhir.base-url}")
    private String baseUrl;

    /** Contexto de FHIR para manejar recursos en formato FHIR R4. */
    private FhirContext fhirContext;

    /** Cliente genérico para realizar operaciones REST sobre FHIR. */
    private IGenericClient client;


    /**
     * Inicializa el contexto de FHIR y el cliente tras la construcción de la clase.
     *
     * <p>Se ejecuta automáticamente después de la creación de la instancia.</p>
     */
    @PostConstruct
    public void init() {
        this.fhirContext = FhirContext.forR4();
        this.client = fhirContext.newRestfulGenericClient(baseUrl);
    }
}
