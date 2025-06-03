package com.myetl.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ResultData {
    private String resultId;
    private String valueType;
    private String testCode;
    private String testName;
    private String resultValue;
    private String units;
    private String referenceRange;
    private String abnormalFlag;
    private String resultDateTime;
    private String technician;
}