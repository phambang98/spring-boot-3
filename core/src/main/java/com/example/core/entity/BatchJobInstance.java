package com.example.core.entity;

import jakarta.persistence.*;



@Entity
@Table(name = "BATCH_JOB_INSTANCE")
public class BatchJobInstance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "JOB_INSTANCE_ID")
    private Long jobInstanceId;

    @Column(name = "VERSION")
    private Integer version;

    @Column(name = "job_name")
    private String jobName;

    @Column(name = "job_key")
    private String jobKey;

    public Long getJobInstanceId() {
        return jobInstanceId;
    }

    public void setJobInstanceId(Long jobInstanceId) {
        this.jobInstanceId = jobInstanceId;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getJobKey() {
        return jobKey;
    }

    public void setJobKey(String jobKey) {
        this.jobKey = jobKey;
    }
}
