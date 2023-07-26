package com.example.core.model;

import com.example.core.utils.DateUtils;

import java.util.Date;

public class JobModelBean {
    private String triggerType;

    private String code;

    private Integer repeatInterval = 1;

    private Integer repeatCount = 0;

    private String cronExpression;

    private String className;

    private String startDate;

    private String endDate;

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public Date getEndDate() {
        return DateUtils.convertStringtoDate(this.endDate, "dd/MM/yyyy hh:mm:ss");
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getCronExpression() {
        return cronExpression;
    }

    public void setCronExpression(String cronExpression) {
        this.cronExpression = cronExpression;
    }

    public Integer getRepeatCount() {
        return repeatCount;
    }

    public void setRepeatCount(Integer repeatCount) {
        this.repeatCount = repeatCount;
    }

    public Integer getRepeatInterval() {
        return repeatInterval;
    }

    public void setRepeatInterval(Integer repeatInterval) {
        this.repeatInterval = repeatInterval;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Date getStartDate() {
        return DateUtils.convertStringtoDate(this.startDate, "dd/MM/yyyy hh:mm:ss");
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getTriggerType() {
        return triggerType;
    }

    public void setTriggerType(String triggerType) {
        this.triggerType = triggerType;
    }
}
