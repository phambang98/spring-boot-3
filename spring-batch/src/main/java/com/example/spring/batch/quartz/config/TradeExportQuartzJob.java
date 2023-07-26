package com.example.spring.batch.quartz.config;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.batch.support.PropertiesConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import java.util.Properties;

@Component
@DisallowConcurrentExecution
public class TradeExportQuartzJob extends QuartzJobBean {

    @Autowired
    private JobOperator jobOperator;

    @Override
    protected void executeInternal(JobExecutionContext context) {
        try {
            Properties properties = PropertiesConverter.stringToProperties(String.format("requestId=%s", System.currentTimeMillis()));
            this.jobOperator.start("tradeExportXmlJob", properties);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
