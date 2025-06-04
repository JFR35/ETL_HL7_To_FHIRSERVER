package com.myetl.service;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.rest.client.api.IGenericClient;
import ca.uhn.fhir.rest.client.interceptor.BasicAuthInterceptor;
import com.myetl.model.ObservationData;
import com.myetl.model.OruR01JsonDto;
import com.myetl.model.ResultData;
import org.hl7.fhir.r4.model.Bundle;
import org.hl7.fhir.r4.model.Observation;
import org.hl7.fhir.r4.model.Patient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

/**
 * Servicio principal que orquesta el procesamiento de mensajes HL7v2 transformados a JSON.
 * Se encarga de autenticarse(credenciales en GUI de Aibbox), mapear el JSON a recursos FHIR,
 * validarlos y enviarlos a un servidor FHIR (Aidbox).
 */
@Service
public class FhirProcessorService {

    private static final Logger logger = LoggerFactory.getLogger(FhirProcessorService.class);

    private final FhirContext fhirContext;
    private final IGenericClient fhirClient;
    private final FhirMapperService fhirMapperService;
    private final FhirValidationService fhirValidationService;

    // Valores inyectados desde application.properties para Basic Auth de Aidbox
    @Value("${aidbox.auth.username}")
    private String aidboxAuthUsername;
    @Value("${aidbox.auth.password}")
    private String aidboxAuthPassword;

    // Constructor para inyección de dependencias
    public FhirProcessorService(
            FhirMapperService fhirMapperService,
            FhirValidationService fhirValidationService,
            @Value("${aidbox.fhir.base-url}") String aidboxBaseUrl, // Inyecta URL de la GUI de Aidbox
            @Value("${aidbox.auth.username}") String aidboxAuthUsername, // Inyecta el usuario
            @Value("${aidbox.auth.password}") String aidboxAuthPassword  // Inyecta la contraseña
    ) {
        this.fhirMapperService = fhirMapperService;
        this.fhirValidationService = fhirValidationService;

        this.aidboxAuthUsername = aidboxAuthUsername;
        this.aidboxAuthPassword = aidboxAuthPassword;

        this.fhirContext = FhirContext.forR4();

        // Configurar el cliente FHIR con Basic Auth directamente
        this.fhirClient = fhirContext.newRestfulGenericClient(aidboxBaseUrl);
        this.fhirClient.registerInterceptor(new BasicAuthInterceptor(this.aidboxAuthUsername, this.aidboxAuthPassword));

        logger.info("FhirProcessorService inicializado y cliente FHIR configurado con Basic Auth.");
    }

    /**
     * Comentado, ya que no admite representación como bundle de Patient/Observation
     * Método principal para procesar el DTO ORU R01.
     * Orquesta la transformación, validación y envío a Aidbox.
     *
     * @param oruR01JsonDto El DTO JSON recibido desde Mirth Connect.
     * @throws Exception Si ocurre un error durante el procesamiento, validación o envío.
     */
    /*
    public void processOruR01(OruR01JsonDto oruR01JsonDto) throws Exception {
        logger.info("Iniciando procesamiento de DTO ORU R01...");

        // 1. Mapear PatientData a Recurso FHIR Patient
        Patient patient = fhirMapperService.mapToPatientFhirResource(oruR01JsonDto.getPatientData());

        // 2. Validar Patient
        fhirValidationService.validateResource(patient);

        // 3. Enviar Patient a Aidbox y obtener el ID asignado por Aidbox
        Patient createdPatient = (Patient) fhirClient.create().resource(patient).execute().getResource();
        logger.info("Recurso Patient creado en Aidbox con ID: {}", createdPatient.getIdElement().getIdPart());


        // 4. Mapear ObservationData y ResultData a Recurso FHIR Observation
        // No necesitas Optional.ofNullable() si ya son Optional.
        Optional<ObservationData> optionalObservationData = Optional.ofNullable(oruR01JsonDto.getObservationData()); // Atención con Optional
        Optional<ResultData> optionalResultData = Optional.ofNullable(oruR01JsonDto.getResultData()); // Ojo con Optional

        if (optionalObservationData.isPresent() && optionalResultData.isPresent()) {
            Observation observation = fhirMapperService.mapToObservationFhirResource(
                    optionalObservationData.get(),
                    optionalResultData.get(),
                    createdPatient.getIdElement().toUnqualifiedVersionless() // Referencia al paciente creado
            );

            // 5. Validar Observation
            fhirValidationService.validateResource(observation);

            // 6. Enviar Observation a Aidbox
            fhirClient.create().resource(observation).execute();
            logger.info("Recurso Observation creado en Aidbox.");
        } else {
            logger.warn("No se encontraron datos de Observation o Result en el DTO. No se creará el recurso Observation FHIR.");
        }

        logger.info("Procesamiento de DTO ORU R01 completado exitosamente.");
    }
        */

    /**
     * Representación Bundle para exponer los Recursos en una misma transación mostrando Patient + Observation
     * @param oruR01JsonDto DTO
     * @throws Exception Retorna cualquier excepción durante el proceso.
     */
    public void processOruR01(OruR01JsonDto oruR01JsonDto) throws Exception {
        logger.info("Iniciando procesamiento de DTO ORU R01 para crear Bundle de transacción...");

        // Crear un Bundle de tipo TRANSACTION
        Bundle bundle = new Bundle();
        bundle.setType(Bundle.BundleType.TRANSACTION);

        // 1. Mapear PatientData a Recurso FHIR Patient
        Patient patient = fhirMapperService.mapToPatientFhirResource(oruR01JsonDto.getPatientData());

        // Generar un ID temporal (URN UUID) para el Patient dentro del Bundle.
        String patientFullUrl = "urn:uuid:" + UUID.randomUUID().toString();
        patient.setId(patientFullUrl); // Asignar el ID temporal al recurso Patient

        // Validar Patient antes de añadirlo al Bundle
        fhirValidationService.validateResource(patient);

        // Añadir el Patient al Bundle como una entrada
        Bundle.BundleEntryComponent patientEntry = bundle.addEntry();
        patientEntry.setFullUrl(patientFullUrl); // El fullUrl es el ID temporal
        patientEntry.setResource(patient);
        // Configurar la operación para el Patient: PUT (upsert) basado en identificador
        patientEntry.getRequest()
                .setMethod(Bundle.HTTPVerb.PUT)
                .setUrl("Patient?identifier=" + patient.getIdentifierFirstRep().getSystem() + "|" + patient.getIdentifierFirstRep().getValue());


        // 2. Mapear ObservationData y ResultData a Recurso FHIR Observation
        Optional<ObservationData> optionalObservationData = Optional.ofNullable(oruR01JsonDto.getObservationData());
        Optional<ResultData> optionalResultData = Optional.ofNullable(oruR01JsonDto.getResultData());

        if (optionalObservationData.isPresent() && optionalResultData.isPresent()) {
            // Mapear la Observation, referenciando al Patient con su fullUrl temporal
            Observation observation = fhirMapperService.mapToObservationFhirResource(
                    optionalObservationData.get(),
                    optionalResultData.get(),
                    patientFullUrl // Referencia al Patient usando su fullUrl dentro del Bundle
            );

            // Validar Observation antes de añadirla al Bundle
            fhirValidationService.validateResource(observation);

            // Añadir la Observation al Bundle como una entrada
            Bundle.BundleEntryComponent observationEntry = bundle.addEntry();
            observationEntry.setFullUrl("urn:uuid:" + UUID.randomUUID().toString()); // Nuevo fullUrl para la Observation
            observationEntry.setResource(observation);
            // Configurar la operación para la Observation: POST (crear)
            observationEntry.getRequest()
                    .setMethod(Bundle.HTTPVerb.POST)
                    .setUrl("Observation");

        } else {
            logger.warn("No se encontraron datos de Observation o Result en el DTO. No se añadirán al Bundle.");
        }

        // 3. Enviar el Bundle de transacción a Aidbox
        logger.info("Enviando Bundle de transacción a Aidbox...");
        // HAPI FHIR tiene un método específico para transacciones de Bundle
        Bundle responseBundle = fhirClient.transaction().withBundle(bundle).execute();

        logger.info("Bundle de transacción procesado en Aidbox. Respuesta:\n{}",
                fhirContext.newJsonParser().setPrettyPrint(true).encodeResourceToString(responseBundle));

        // Opcional: Extraer los IDs reales asignados por Aidbox
        responseBundle.getEntry().forEach(entry -> {
            if (entry.getResponse() != null && entry.getResponse().getLocation() != null) {
                logger.info("Recurso {} procesado con ID final: {}", entry.getResponse().getLocation(), entry.getResponse().getLocationElement().getIdBase());
            }
        });

        logger.info("Procesamiento de DTO ORU R01 completado exitosamente.");
    }
}