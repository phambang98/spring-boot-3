package com.example.spring.batch.job;

import com.example.spring.batch.configuration.BatchConfiguration;
import com.example.spring.batch.tasklet.FileInputTasklet;
import com.example.core.entity.Student;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.builder.JpaItemWriterBuilder;
import org.springframework.batch.item.json.JacksonJsonObjectReader;
import org.springframework.batch.item.json.JsonItemReader;
import org.springframework.batch.item.json.builder.JsonItemReaderBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class StudentImportJsonConfiguration extends BatchConfiguration {

    @Bean
    public Job studentImportJsonJob(JobRepository jobRepository, @Qualifier("existsFileStudentJsonStep") Step existsFileStudentJsonStep,
                                    @Qualifier("studentImportJsonStep") Step studentImportJsonStep) {
        return new JobBuilder("studentImportJsonJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(existsFileStudentJsonStep).on("FAILED").end()
                .from(existsFileStudentJsonStep).on("COMPLETED")
                .to(studentImportJsonStep).end()
                .listener(jobNotificationListener)
                .build();
    }

    @Bean
    public Step existsFileStudentJsonStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("existsFileStudentJsonStep", jobRepository)
                .tasklet(FileInputTasklet.builder().fileName("student.json"), transactionManager)
                .listener(stepNotificationListener)
                .build();
    }

    @Bean
    public Step studentImportJsonStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("studentImportJsonStep", jobRepository)
                .<Student, Student>chunk(chunkSize, transactionManager)
                .reader(studentJsonItemReader())
                .writer(studentImportJsonItemWriter(null))
                .faultTolerant()
                .skip(Exception.class)
                .skipPolicy(skipPolicy())
                .listener(stepNotificationListener)
                .build();
    }

    @Bean
    @StepScope
    public JsonItemReader<Student> studentJsonItemReader() {
        return new JsonItemReaderBuilder<Student>()
                .jsonObjectReader(new JacksonJsonObjectReader<>(Student.class))
                .resource(getPathFileInput("student.json"))
                .saveState(false)
                .name("studentJsonItemReader")
                .build();
    }

    @Bean
    @StepScope
    public JpaItemWriter<Student> studentImportJsonItemWriter(EntityManagerFactory entityManagerFactory) {
        return new JpaItemWriterBuilder<Student>()
                .usePersist(false)
                .entityManagerFactory(entityManagerFactory)
                .build();
    }

}
