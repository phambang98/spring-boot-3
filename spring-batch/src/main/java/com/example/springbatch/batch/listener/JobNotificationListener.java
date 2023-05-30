package com.example.springbatch.batch.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.CompositeJobExecutionListener;
import org.springframework.stereotype.Component;

@Component
public class JobNotificationListener extends CompositeJobExecutionListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(JobNotificationListener.class);


    @Override
    public void beforeJob(JobExecution jobExecution) {
        LOGGER.info("Start Job {}", jobExecution.getJobInstance().getJobName());
        // set jobName and jobId and pass to execution context
        jobExecution.getExecutionContext().put("jobName", jobExecution.getJobInstance().getJobName());
        jobExecution.getExecutionContext().put("jobId", jobExecution.getId());
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        LOGGER.info("End Job {}", jobExecution.getJobInstance().getJobName());
    }
}