package com.example.core.entity;

import jakarta.persistence.*;

import java.util.Date;


@Entity
@Table(name = "BATCH_STEP_EXECUTION")
public class BatchStepExecution {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "STEP_EXECUTION_ID")
    private Long stepExecutionId;

    @Column(name = "VERSION")
    private Integer version;

    @Column(name = "STEP_NAME")
    private Long stepName;

    @Column(name = "JOB_EXECUTION_ID")
    private Integer jobExecutionId;

    @Column(name = "CREATE_TIME")
    private Date createTime;

    @Column(name = "START_TIME")
    private Date startTime;

    @Column(name = "END_TIME")
    private Date endTime;

    @Column(name = "STATUS")
    private String status;

    @Column(name = "COMMIT_COUNT")
    private Integer commitCount;

    @Column(name = "READ_COUNT")
    private Integer readCount;

    @Column(name = "FILTER_COUNT")
    private Integer filterCount;

    @Column(name = "WRITE_COUNT")
    private Integer writeCount;

    @Column(name = "READ_SKIP_COUNT")
    private Integer readSkipCount;

    @Column(name = "WRITE_SKIP_COUNT")
    private Integer writeSkipCount;

    @Column(name = "PROCESS_SKIP_COUNT")
    private Integer processSkipCount;

    @Column(name = "ROLLBACK_COUNT")
    private Integer rollbackCount;

    @Column(name = "EXIT_CODE")
    private String exitCode;

    @Column(name = "EXIT_MESSAGE")
    private String exitMessage;

    @Column(name = "LAST_UPDATED")
    private String lastUpdated;

    public Long getStepExecutionId() {
        return stepExecutionId;
    }

    public void setStepExecutionId(Long stepExecutionId) {
        this.stepExecutionId = stepExecutionId;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Long getStepName() {
        return stepName;
    }

    public void setStepName(Long stepName) {
        this.stepName = stepName;
    }

    public Integer getJobExecutionId() {
        return jobExecutionId;
    }

    public void setJobExecutionId(Integer jobExecutionId) {
        this.jobExecutionId = jobExecutionId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getCommitCount() {
        return commitCount;
    }

    public void setCommitCount(Integer commitCount) {
        this.commitCount = commitCount;
    }

    public Integer getReadCount() {
        return readCount;
    }

    public void setReadCount(Integer readCount) {
        this.readCount = readCount;
    }

    public Integer getFilterCount() {
        return filterCount;
    }

    public void setFilterCount(Integer filterCount) {
        this.filterCount = filterCount;
    }

    public Integer getWriteCount() {
        return writeCount;
    }

    public void setWriteCount(Integer writeCount) {
        this.writeCount = writeCount;
    }

    public Integer getReadSkipCount() {
        return readSkipCount;
    }

    public void setReadSkipCount(Integer readSkipCount) {
        this.readSkipCount = readSkipCount;
    }

    public Integer getWriteSkipCount() {
        return writeSkipCount;
    }

    public void setWriteSkipCount(Integer writeSkipCount) {
        this.writeSkipCount = writeSkipCount;
    }

    public Integer getProcessSkipCount() {
        return processSkipCount;
    }

    public void setProcessSkipCount(Integer processSkipCount) {
        this.processSkipCount = processSkipCount;
    }

    public Integer getRollbackCount() {
        return rollbackCount;
    }

    public void setRollbackCount(Integer rollbackCount) {
        this.rollbackCount = rollbackCount;
    }

    public String getExitCode() {
        return exitCode;
    }

    public void setExitCode(String exitCode) {
        this.exitCode = exitCode;
    }

    public String getExitMessage() {
        return exitMessage;
    }

    public void setExitMessage(String exitMessage) {
        this.exitMessage = exitMessage;
    }

    public String getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(String lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
}
