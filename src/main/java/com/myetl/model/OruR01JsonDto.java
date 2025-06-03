package com.myetl.model;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Optional;


/**
 * Representación del mensaje HL7v2 transformado en JSON para su procesamiento.
 *
 * <p>Esta clase define la estructura del JSON recibido desde Mirth Connect.</p>
 * <p>Incluye información del encabezado del mensaje, datos del paciente y
 * opcionalmente datos de visita y observaciones.</p>
 */
@Getter
@Setter
@NoArgsConstructor
public class OruR01JsonDto {

    private MessageHeader messageHeader;
    private PatientData patientData;
    private VisitData visitData;
    private ObservationData observationData;
    private ResultData resultData;

    // private String test;
}
