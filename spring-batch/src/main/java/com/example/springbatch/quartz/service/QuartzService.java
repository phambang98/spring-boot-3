package com.example.springbatch.quartz.service;


import com.example.springcore.model.JobModelBean;
import com.example.springbatch.quartz.constant.JobConstant;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


@Service
public class QuartzService {

    @Autowired
    private Scheduler scheduler;

    public ResponseEntity<String> createJob(JobModelBean jobModelBean) throws Exception {

        JobConstant jobConstant = JobConstant.getJobByName(jobModelBean.getCode());
        if (jobConstant == null) {
            return new ResponseEntity<>("Not Found Job", HttpStatus.NOT_FOUND);
        }

        JobDetail jobDetail = JobBuilder.newJob(jobConstant.getJobClass())
                .withIdentity(jobConstant.getJobName(), Scheduler.DEFAULT_GROUP)
                .withDescription(jobConstant.getJobDescription())
                .build();
        if (scheduler.checkExists(jobDetail.getKey())) {
            scheduler.deleteJob(jobDetail.getKey());
        }

        if (jobModelBean.getTriggerType().equalsIgnoreCase("SimpleTrigger")) {
            SimpleTrigger simpleTrigger = TriggerBuilder
                    .newTrigger().forJob(jobDetail)
                    .startAt(jobModelBean.getStartDate())
                    .withIdentity(jobModelBean.getCode(), Scheduler.DEFAULT_GROUP)
                    .withSchedule(
                            SimpleScheduleBuilder.simpleSchedule()
                                    .withIntervalInSeconds(jobModelBean.getRepeatInterval())
                                    .withRepeatCount(jobModelBean.getRepeatCount() == null ? SimpleTrigger.REPEAT_INDEFINITELY : jobModelBean.getRepeatCount())
                    ).build();
            scheduler.scheduleJob(jobDetail, simpleTrigger);
        } else {
            CronTrigger cronTrigger = TriggerBuilder
                    .newTrigger()
                    .forJob(jobDetail)
                    .startAt(jobModelBean.getStartDate())
                    .endAt(jobModelBean.getEndDate())
                    .withIdentity(jobModelBean.getCode(), Scheduler.DEFAULT_GROUP)
                    .withSchedule(
                            CronScheduleBuilder
                                    .cronSchedule(jobModelBean.getCronExpression())
                                    .withMisfireHandlingInstructionIgnoreMisfires())
                    .build();

            scheduler.scheduleJob(jobDetail, cronTrigger);
        }

        return new ResponseEntity<>("OK", HttpStatus.CREATED);
    }
}
