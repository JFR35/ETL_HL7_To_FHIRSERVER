package com.myetl.model;

/**
 * Representación del mensaje HL7v2 transformado en JSON para su procesamiento.
 *
 * <p>Esta clase define la estructura del JSON recibido desde Mirth Connect.</p>
 * <p>Incluye información del encabezado del mensaje, datos del paciente y
 * opcionalmente datos de visita y observaciones.</p>
 * <p>Hay otras clases POJOs para los DTO individuales en cada segmento</p>
 * <p>Se deja de usar Lombock porque estaba dando problemas con los Getter/Setter</p>
 */
public class OruR01JsonDto {

    private MessageHeader messageHeader;
    private PatientData patientData;
    private VisitData visitData;
    private ObservationData observationData;
    private ResultData resultData;

    // private String test;

    public MessageHeader getMessageHeader() {
        return messageHeader;
    }

    public void setMessageHeader(MessageHeader messageHeader) {
        this.messageHeader = messageHeader;
    }

    public PatientData getPatientData() {
        return patientData;
    }

    public void setPatientData(PatientData patientData) {
        this.patientData = patientData;
    }

    public VisitData getVisitData() {
        return visitData;
    }

    public void setVisitData(VisitData visitData) {
        this.visitData = visitData;
    }

    public ObservationData getObservationData() {
        return observationData;
    }

    public void setObservationData(ObservationData observationData) {
        this.observationData = observationData;
    }

    public ResultData getResultData() {
        return resultData;
    }

    public void setResultData(ResultData resultData) {
        this.resultData = resultData;
    }
}
