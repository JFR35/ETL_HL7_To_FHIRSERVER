package com.myetl.model;

public class MessageHeader {
    private String messageType;
    private String sendingApplication;
    private String receivingApplication;
    private String sendingFacility;
    private String receivingFacility;
    private String messageControlId;
    private String versionId;
    private String timestamp;

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public String getSendingApplication() {
        return sendingApplication;
    }

    public void setSendingApplication(String sendingApplication) {
        this.sendingApplication = sendingApplication;
    }

    public String getReceivingApplication() {
        return receivingApplication;
    }

    public void setReceivingApplication(String receivingApplication) {
        this.receivingApplication = receivingApplication;
    }

    public String getSendingFacility() {
        return sendingFacility;
    }

    public void setSendingFacility(String sendingFacility) {
        this.sendingFacility = sendingFacility;
    }

    public String getReceivingFacility() {
        return receivingFacility;
    }

    public void setReceivingFacility(String receivingFacility) {
        this.receivingFacility = receivingFacility;
    }

    public String getMessageControlId() {
        return messageControlId;
    }

    public void setMessageControlId(String messageControlId) {
        this.messageControlId = messageControlId;
    }

    public String getVersionId() {
        return versionId;
    }

    public void setVersionId(String versionId) {
        this.versionId = versionId;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
