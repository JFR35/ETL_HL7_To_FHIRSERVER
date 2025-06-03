// src/main/java/com/myetl/model/ObservationData.java
package com.myetl.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Data; // Asegúrate de tener @Data si no está ya

@Getter
@Setter
@NoArgsConstructor
public class ObservationData {
    private String orderId;
    private String testCode;
    private String testName;
    private String observationDateTime;
    private String technician;
}