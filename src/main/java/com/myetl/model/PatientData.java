package com.myetl.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PatientData {
    private String patientId;
    private String name; // Cambiar de `patientName` a `name`
    //private String patientName; parece que no coincide con el JSON recibido
    private String birthDate;
    private String gender;
    private String address;
    private String phone;
}
