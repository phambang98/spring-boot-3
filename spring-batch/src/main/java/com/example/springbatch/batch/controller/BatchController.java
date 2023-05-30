package com.example.springbatch.batch.controller;

import com.example.springbatch.batch.service.BatchJobInstanceService;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionException;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/batch")
public class BatchController {
    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private JobOperator jobOperator;

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private BatchJobInstanceService batchJobInstanceService;

    @GetMapping(path = "/start/{jobName}") // Start batch process path
    public ResponseEntity<String> startBatch(@PathVariable String jobName) throws JobExecutionException {
        JobParameters jobParameters = new JobParametersBuilder()
                .addDate("date", new Date())
                .addLong("time", System.currentTimeMillis()).toJobParameters();
        Job job = (Job) applicationContext.getBean(jobName);
//        if (batchJobInstanceService.existsByJobName(jobName)) {
//            StringBuilder param = new StringBuilder();
//            jobParameters.getParameters().entrySet().stream().forEach(map -> {
//                        param.append(map.getKey()).append("=").append(map.getValue()).append(",");
//                    }
//            );
//            this.jobOperator.start(jobName, param.toString());
//        } else {
            jobLauncher.run(job, jobParameters);
//        }
        return new ResponseEntity<>("Batch Process started!!", HttpStatus.OK);
    }
}