package com.example.spring.batch.quartz.constant;

import com.example.spring.batch.quartz.config.*;
import org.quartz.Job;

public enum JobConstant {

    CLIENT_EXPORT_JOB("usersExportJob", UsersQuartzJob.class, "Users export file csv"),
    COFFEE_IMPORT_JOB("coffeeJob", CoffeeQuartzJob.class, "Coffee import from file csv"),
    MENU_EXPORT_JSON_JOB("menuExportJsonJob", MenuExportJsonQuartzJob.class, "Menu export from file json"),
    TRADE_IMPORT_XML_JOB("tradeImportXmlJob", TradeImportQuartzJob.class, "Trade import from file xml"),
    TRADE_EXPORT_XML_JOB("tradeExportXmlJob", TradeExportQuartzJob.class, "Trade export file xml"),
    STUDENT_IMPORT_JSON_JOB("studentImportJsonJob", StudentImportQuartzJob.class, "Student import file json"),
    MULTI_FILE_IMPORT_JOB("multiFileImportJob", MultiFileImportQuartzJob.class, "Multi file import (csv,json,xml)");


    private final String jobName;
    private final Class<? extends Job> jobClass;

    private final String jobDescription;

    JobConstant(String jobName, Class<? extends Job> jobClass, String jobDescription) {
        this.jobName = jobName;
        this.jobClass = jobClass;
        this.jobDescription = jobDescription;
    }


    public static JobConstant getJobByName(String jobName) {
        for (JobConstant jobConstant : JobConstant.values()) {
            if (jobConstant.jobName.equals(jobName)) {
                return jobConstant;
            }
        }
        return null;
    }

    public String getJobName() {
        return jobName;
    }

    public Class<? extends Job> getJobClass() {
        return jobClass;
    }

    public String getJobDescription() {
        return jobDescription;
    }
}
