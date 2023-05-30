package com.example.springcore.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "BATCH_JOB_EXECUTION_PARAMS")
public class BatchJobExecutionParams {
    @Id
    @Column(name = "JOB_EXECUTION_ID")
    private Long jobExecutionId;

    @Column(name = "PARAMETER_NAME")
    private String parameterName;

    @Column(name = "PARAMETER_TYPE")
    private String parameterType;

    @Column(name = "PARAMETER_VALUE")
    private String parameterValue;

    @Column(name = "IDENTIFYING")
    private String identifying;

    public Long getJobExecutionId() {
        return jobExecutionId;
    }

    public void setJobExecutionId(Long jobExecutionId) {
        this.jobExecutionId = jobExecutionId;
    }

    public String getParameterName() {
        return parameterName;
    }

    public void setParameterName(String parameterName) {
        this.parameterName = parameterName;
    }

    public String getParameterType() {
        return parameterType;
    }

    public void setParameterType(String parameterType) {
        this.parameterType = parameterType;
    }

    public String getParameterValue() {
        return parameterValue;
    }

    public void setParameterValue(String parameterValue) {
        this.parameterValue = parameterValue;
    }

    public String getIdentifying() {
        return identifying;
    }

    public void setIdentifying(String identifying) {
        this.identifying = identifying;
    }
}
