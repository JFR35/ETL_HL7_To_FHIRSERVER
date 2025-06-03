package com.myetl.model;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MessageHeader {
    private String messageType;
    private String sendingApplication;
    private String receivingApplication;
    private String sendingFacility;
    private String receivingFacility;
    private String messageControlId;
    private String versionId;
    private String timestamp;
}
