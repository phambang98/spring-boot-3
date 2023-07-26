package com.example.spring.batch.job;

import com.example.spring.batch.configuration.BatchConfiguration;
import com.example.core.enums.BatchExitStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.core.job.flow.support.SimpleFlow;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MultiFileImportConfiguration extends BatchConfiguration {

    @Bean
    public Job multiFileImportJob(JobRepository jobRepository) {
        return new JobBuilder("multiFileImportJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(multiFileImportFlow())
                .build()
                .listener(jobNotificationListener)
                .build();
    }

    @Bean
    public Flow multiFileImportFlow() {
        return new FlowBuilder<SimpleFlow>("multiFileImportFlow")
                .split(batchTaskExecutor())
                .add(importFileCsvFlow(null, null, null, null),
                        importFileXmlFlow(null, null), importFileJsonFlow(null, null))
                .build();
    }

    @Bean
    public Flow importFileCsvFlow(@Qualifier("checkExistsFileCoffeeStep") Step checkExistsFileCoffeeStep,
                                  @Qualifier("coffeeUploadFileStep") Step coffeeUploadFileStep,
                                  @Qualifier("coffeePreStep") Step coffeePreStep,
                                  @Qualifier("removeCoffeeUploadFileStep") Step removeCoffeeUploadFileStep) {
        return new FlowBuilder<SimpleFlow>("importFileCsvFlow")
                .start(checkExistsFileCoffeeStep).on(BatchExitStatus.FAILED.name()).end()
                .from(checkExistsFileCoffeeStep).on(BatchExitStatus.COMPLETED.name())
                .to(coffeeUploadFileStep)
                .next(coffeePreStep)
                .next(removeCoffeeUploadFileStep)
                .build();
    }

    @Bean
    public Flow importFileXmlFlow(@Qualifier("checkExistsFileTradeStep") Step checkExistsFileTradeStep,
                                  @Qualifier("tradeImportXmlStep") Step tradeImportXmlStep) {
        return new FlowBuilder<SimpleFlow>("importFileXmlFlow")
                .start(checkExistsFileTradeStep).on(BatchExitStatus.FAILED.name()).end()
                .from(checkExistsFileTradeStep).on(BatchExitStatus.COMPLETED.name())
                .to(tradeImportXmlStep)
                .build();
    }

    @Bean
    public Flow importFileJsonFlow(@Qualifier("existsFileStudentJsonStep") Step existsFileStudentJsonStep,
                                   @Qualifier("studentImportJsonStep") Step studentImportJsonStep) {
        return new FlowBuilder<SimpleFlow>("importFileJsonFlow")
                .start(existsFileStudentJsonStep).on(BatchExitStatus.FAILED.name()).end()
                .from(existsFileStudentJsonStep).on(BatchExitStatus.COMPLETED.name())
                .to(studentImportJsonStep)
                .build();
    }
}
