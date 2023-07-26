package com.example.spring.batch.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.*;
import org.springframework.batch.core.listener.CompositeStepExecutionListener;
import org.springframework.stereotype.Component;

@Component
public class StepNotificationListener extends CompositeStepExecutionListener {
    Logger logger = LoggerFactory.getLogger(StepNotificationListener.class);

    @Override
    public void beforeStep(StepExecution stepExecution) {
        logger.info("Before Step");
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        logger.info("After Step");
        return null;
    }

}
