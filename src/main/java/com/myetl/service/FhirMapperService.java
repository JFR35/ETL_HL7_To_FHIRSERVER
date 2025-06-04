package com.myetl.service;

import com.myetl.model.PatientData;
import com.myetl.model.ObservationData;
import com.myetl.model.ResultData;

import org.hl7.fhir.r4.model.Patient;
import org.hl7.fhir.r4.model.Observation;
import org.hl7.fhir.r4.model.Reference;
import org.hl7.fhir.r4.model.HumanName;
import org.hl7.fhir.r4.model.Enumerations;
import org.hl7.fhir.r4.model.CodeableConcept;
import org.hl7.fhir.r4.model.Coding;
import org.hl7.fhir.r4.model.Quantity;
import org.hl7.fhir.r4.model.DateTimeType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Servicio encargado de mapear los DTOs de JSON que recibimos desde Mirth
 * a recursos FHIR Patient, Observation.
 */
@Service
public class FhirMapperService {

    private static final Logger logger = LoggerFactory.getLogger(FhirMapperService.class);

    public FhirMapperService() {
        logger.info("FhirMapperService inicializado.");// Auditoria
    }

    /**
     * Mapea PatientData del DTO a un recurso FHIR Patient.
     * @param patientData Los datos del paciente del DTO.
     * @return Un recurso FHIR Patient.
     */
    public Patient mapToPatientFhirResource(PatientData patientData) {
        Patient patient = new Patient();

        // Identificador del paciente (ej. DNI/NIE)
        patient.addIdentifier()
                .setSystem("urn:oid:2.16.840.1.113883.2.4.6.3") // OID para DNI/NIE
                .setValue(patientData.getPatientId())
                .setType(new CodeableConcept(new Coding("http://terminology.hl7.org/CodeSystem/v2-0203", "NI", "National Identifier")));

        // Nombre del paciente
        HumanName name = patient.addName();
        String[] nameParts = patientData.getName().split(" ", 2);
        if (nameParts.length > 1) {
            name.setFamily(nameParts[1].trim());
            name.addGiven(nameParts[0].trim());
        } else {
            name.setText(patientData.getName());
        }

        // Género administrativo
        if ("M".equalsIgnoreCase(patientData.getGender())) {
            patient.setGender(Enumerations.AdministrativeGender.MALE);
        } else if ("F".equalsIgnoreCase(patientData.getGender())) {
            patient.setGender(Enumerations.AdministrativeGender.FEMALE);
        } else {
            patient.setGender(Enumerations.AdministrativeGender.UNKNOWN);
        }

        // Fecha de Nacimiento
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
            patient.setBirthDate(dateFormat.parse(patientData.getBirthDate()));
        } catch (ParseException e) {
            logger.warn("No se pudo parsear la fecha de nacimiento del paciente '{}': {}", patientData.getBirthDate(), e.getMessage()); // Auditoria
        }

        // Dirección
        if (patientData.getAddress() != null && !patientData.getAddress().isEmpty()) {
            patient.addAddress().setText(patientData.getAddress());
        }

        // Teléfono
        if (patientData.getPhone() != null && !patientData.getPhone().isEmpty()) {
            patient.addTelecom()
                    .setSystem(org.hl7.fhir.r4.model.ContactPoint.ContactPointSystem.PHONE)
                    .setValue(patientData.getPhone())
                    .setUse(org.hl7.fhir.r4.model.ContactPoint.ContactPointUse.HOME);
        }

        return patient;
    }

    /**
     * Mapea ObservationData y ResultData del DTO a un recurso FHIR Observation.
     * @param observationData Los datos de la observación del DTO.
     * @param resultData Los datos del resultado del DTO.
     * @param patientFhirId La referencia al ID FHIR del paciente (ej. "Patient/123").
     * @return Un recurso FHIR Observation.
     */
    public Observation mapToObservationFhirResource(ObservationData observationData, ResultData resultData, String patientFhirId) {
        Observation observation = new Observation();

        // Estado de la observación
        observation.setStatus(Observation.ObservationStatus.FINAL);

        // Categoría: Laboratorio
        observation.addCategory(new CodeableConcept(new Coding("http://terminology.hl7.org/CodeSystem/observation-category", "laboratory", "Laboratory")));

        // Código de la observación son terminología LOINC para Glucosa típico en pruebas de laboratoria, recordar que el sistema origen es un LIS
        observation.setCode(new CodeableConcept(new Coding("http://loinc.org", observationData.getTestCode(), observationData.getTestName())));

        // Sujeto (referencia al paciente)
        observation.setSubject(new Reference(patientFhirId));

        // Fecha/Hora de la observación
        try {
            SimpleDateFormat datetimeFormat = new SimpleDateFormat("yyyyMMddHHmmss");
            Date obsDate = datetimeFormat.parse(observationData.getObservationDateTime());
            observation.setIssued(obsDate);
            observation.setEffective(new DateTimeType(obsDate));
        } catch (ParseException e) {
            logger.warn("No se pudo parsear la fecha/hora de la observación '{}': {}", observationData.getObservationDateTime(), e.getMessage());
        }

        // Valor de la observación (Quantity para valores numéricos con unidad)
        try {
            Quantity quantity = new Quantity();
            quantity.setValue(Double.parseDouble(resultData.getResultValue()));
            quantity.setUnit(resultData.getUnits());
            quantity.setSystem("http://unitsofmeasure.org");
            quantity.setCode(resultData.getUnits());
            observation.setValue(quantity);
        } catch (NumberFormatException e) {
            logger.error("No se pudo parsear el valor del resultado '{}' a número: {}", resultData.getResultValue(), e.getMessage());
        }

        // Rango de referencia
        if (resultData.getReferenceRange() != null && !resultData.getReferenceRange().isEmpty()) {
            observation.addReferenceRange().setText(resultData.getReferenceRange());
        }

        // Flag de anormalidad
        if (resultData.getAbnormalFlag() != null && !resultData.getAbnormalFlag().isEmpty()) {
            observation.addInterpretation(new CodeableConcept(new Coding("http://terminology.hl7.org/CodeSystem/v2-0078", resultData.getAbnormalFlag(), resultData.getAbnormalFlag())));
        }

        // Performer (técnico que realizó la observación)
        if (observationData.getTechnician() != null && !observationData.getTechnician().isEmpty()) {
            observation.addPerformer(new Reference().setDisplay(observationData.getTechnician()));
        }

        return observation;
    }
}