package com.myetl.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class VisitData {
    private String patientClass;
    private String attendingDoctor;
    private String admissionType;
    private String admitDateTime;
}
