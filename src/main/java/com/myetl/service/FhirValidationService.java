package com.myetl.service;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.validation.FhirValidator;
import ca.uhn.fhir.validation.ValidationResult;
import org.hl7.fhir.instance.model.api.IBaseResource; // Interfaz base para todos los recursos FHIR

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Servicio encargado de validar recursos FHIR utilizando el validador de HAPI FHIR.
 */
@Service
public class FhirValidationService {

    private static final Logger logger = LoggerFactory.getLogger(FhirValidationService.class);
    private final FhirValidator validator;
    private final FhirContext fhirContext; // Necesitamos el contexto para serializar recursos en logs

    public FhirValidationService(FhirContext fhirContext) {
        this.fhirContext = fhirContext;
        this.validator = fhirContext.newValidator();
        // Aquí se podría incluir un StructureDefinitionPath para cargar perfiles de validación
        // InstanceValidator module = new InstanceValidator(fhirContext);
        logger.info("FhirValidationService inicializado.");
    }

    /**
     * Valida un recurso FHIR.
     * @param resource El recurso FHIR a validar.
     * @throws RuntimeException Si la validación falla, se lanza una excepción con detalles.
     */
    public void validateResource(IBaseResource resource) {
        ValidationResult validationResult = validator.validateWithResult(resource);

        if (!validationResult.isSuccessful()) {
            String resourceType = resource.fhirType();
            String encodedResource = fhirContext.newJsonParser().setPrettyPrint(true).encodeResourceToString(resource);
            logger.error("¡Validación del recurso FHIR {} FALLIDA!\nRecurso:\n{}", resourceType, encodedResource);
            validationResult.getMessages().forEach(m -> logger.error("  - {}: {}", m.getSeverity(), m.getMessage()));
            throw new RuntimeException(String.format("La validación del recurso %s falló: %s", resourceType, validationResult.getMessages().get(0).getMessage()));
        }
        logger.info("Validación del recurso FHIR {} exitosa.", resource.fhirType());
    }
}