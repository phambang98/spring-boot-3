package com.example.springbatch.quartz.config;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

@Component
@DisallowConcurrentExecution
public class StudentImportQuartzJob extends QuartzJobBean {

    @Autowired
    private JobOperator jobOperator;

    @Override
    protected void executeInternal(JobExecutionContext context) {
        try {
            this.jobOperator.start("studentImportJsonJob", String.format("requestId=%s", System.currentTimeMillis()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
