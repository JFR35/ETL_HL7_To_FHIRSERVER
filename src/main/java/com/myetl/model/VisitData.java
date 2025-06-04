package com.myetl.model;

public class VisitData {
    private String patientClass;
    private String attendingDoctor;
    private String admissionType;
    private String admitDateTime;

    public String getPatientClass() {
        return patientClass;
    }

    public void setPatientClass(String patientClass) {
        this.patientClass = patientClass;
    }

    public String getAttendingDoctor() {
        return attendingDoctor;
    }

    public void setAttendingDoctor(String attendingDoctor) {
        this.attendingDoctor = attendingDoctor;
    }

    public String getAdmissionType() {
        return admissionType;
    }

    public void setAdmissionType(String admissionType) {
        this.admissionType = admissionType;
    }

    public String getAdmitDateTime() {
        return admitDateTime;
    }

    public void setAdmitDateTime(String admitDateTime) {
        this.admitDateTime = admitDateTime;
    }
}
