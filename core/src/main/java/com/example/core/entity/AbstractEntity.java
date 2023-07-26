package com.example.core.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;

import java.util.Date;

@MappedSuperclass
public class AbstractEntity {

    @Column(name = "LAST_APPROVE_BY")
    protected String lastApproveBy;

    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    @Column(name = "LAST_APPROVE_DATE")
    protected Date lastApproveDate;

    @Column(name = "LAST_UPDATE_BY")
    protected String lastUpdateBy;

    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    @Column(name = "LAST_UPDATE_DATE")
    protected Date lastUpdateDate;

    @Column(name = "STATUS")
    protected String status;

    public String getLastApproveBy() {
        return lastApproveBy;
    }

    public void setLastApproveBy(String lastApproveBy) {
        this.lastApproveBy = lastApproveBy;
    }

    public Date getLastApproveDate() {
        return lastApproveDate;
    }

    public void setLastApproveDate(Date lastApproveDate) {
        this.lastApproveDate = lastApproveDate;
    }

    public String getLastUpdateBy() {
        return lastUpdateBy;
    }

    public void setLastUpdateBy(String lastUpdateBy) {
        this.lastUpdateBy = lastUpdateBy;
    }

    public Date getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(Date lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
