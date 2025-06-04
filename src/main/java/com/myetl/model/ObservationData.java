// src/main/java/com/myetl/model/ObservationData.java
package com.myetl.model;



public class ObservationData {
    private String orderId;
    private String testCode;
    private String testName;
    private String observationDateTime;
    private String technician;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getTestCode() {
        return testCode;
    }

    public void setTestCode(String testCode) {
        this.testCode = testCode;
    }

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public String getObservationDateTime() {
        return observationDateTime;
    }

    public void setObservationDateTime(String observationDateTime) {
        this.observationDateTime = observationDateTime;
    }

    public String getTechnician() {
        return technician;
    }

    public void setTechnician(String technician) {
        this.technician = technician;
    }
}