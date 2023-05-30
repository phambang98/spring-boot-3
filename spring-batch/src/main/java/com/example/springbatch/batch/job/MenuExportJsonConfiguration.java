package com.example.springbatch.batch.job;

import com.example.springbatch.batch.configuration.BatchConfiguration;

import com.example.springcore.entity.Menu;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.json.JacksonJsonObjectMarshaller;
import org.springframework.batch.item.json.JsonFileItemWriter;
import org.springframework.batch.item.json.builder.JsonFileItemWriterBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.Date;

@Configuration
public class MenuExportJsonConfiguration extends BatchConfiguration {

    @Bean
    public Job menuExportJsonJob(JobRepository jobRepository, @Qualifier("menuExportJsonStep") Step menuExportJsonStep) {
        return new JobBuilder("menuExportJsonJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(menuExportJsonStep)
                .listener(jobNotificationListener)
                .build();
    }

    @Bean
    public Step menuExportJsonStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) throws Exception {
        return new StepBuilder("menuExportJsonStep", jobRepository)
                .<Menu, Menu>chunk(chunkSize, transactionManager)
                .reader(menuExportJsonReader(null))
//                .processor(menuExportJsonValidateProcessor())
                .writer(menuExportJsonItemWriter())
                .faultTolerant()
                .skip(Exception.class)
                .skipPolicy(skipPolicy())
                .listener(stepNotificationListener)
                .build();
    }

    @Bean
    @StepScope
    public JpaPagingItemReader<Menu> menuExportJsonReader(EntityManagerFactory entityManagerFactory) throws Exception {
        String jpqlQuery = "select f from Menu f";
        JpaPagingItemReader<Menu> reader = new JpaPagingItemReader<>();
        reader.setQueryString(jpqlQuery);
        reader.setEntityManagerFactory(entityManagerFactory);
        reader.setPageSize(100);
        reader.afterPropertiesSet();
        reader.setSaveState(false);
        return reader;
    }

    @Bean
    @StepScope
    public JsonFileItemWriter<Menu> menuExportJsonItemWriter() {
        ObjectMapper objectMapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
        return new JsonFileItemWriterBuilder<Menu>()
                .jsonObjectMarshaller(new JacksonJsonObjectMarshaller<>(objectMapper))
                .resource(getPathFileOutput("menu_" + new Date().getTime() + ".json"))
                .name("menuExportJsonItemWriter")
                .build();
    }
}
