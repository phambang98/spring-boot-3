package com.example.batch.job;

import com.example.batch.configuration.BatchConfiguration;
import com.example.batch.listener.ItemSkipPolicy;
import com.example.batch.mapper.CustomLineMapper;
import com.example.batch.validator.CoffeeBeanValidator;
import com.example.batch.writer.CoffeeWriter;
import com.example.batch.writer.CustomItemWriter;
import com.example.batch.writer.ErrorUploadFileWriter;
import com.example.model.CoffeeBean;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.support.ClassifierCompositeItemWriter;
import org.springframework.batch.item.validator.SpringValidator;
import org.springframework.batch.item.validator.ValidatingItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class CoffeeConfiguration extends BatchConfiguration {

    @Autowired
    private CoffeeBeanValidator coffeeBeanValidator;
    @Autowired
    private CoffeeWriter coffeeWriter;

    @Autowired
    private ErrorUploadFileWriter errorUploadFileWriter;

    @Bean
    public Job coffeeJob(JobRepository jobRepository, @Qualifier("coffeeUploadFileStep") Step coffeeUploadFileStep) {
        return new JobBuilder("coffeeJob", jobRepository)
                .incrementer(new RunIdIncrementer()).start(coffeeUploadFileStep)
                .listener(jobCompletionNotificationListener)
                .build();
    }


    @Bean
    public Step coffeeUploadFileStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("coffeeUploadFileStep", jobRepository)
                .<CoffeeBean, CoffeeBean>chunk(chunkSize, transactionManager)
                .reader(coffeeUploadFileReader())
                .processor(coffeeUploadFileValidateProcessor())
                .writer(classifierCoffeeBeanUploadWriter())
                .faultTolerant()
                .skip(Exception.class)
                .skipPolicy(new ItemSkipPolicy())
                .taskExecutor(batchTaskExecutor())
                .build();
    }

    @Bean
    public ClassifierCompositeItemWriter<CoffeeBean> classifierCoffeeBeanUploadWriter() {
        ClassifierCompositeItemWriter<CoffeeBean> itemWriter = new ClassifierCompositeItemWriter<>();
        itemWriter.setClassifier(new CustomItemWriter<>().success(coffeeWriter).error(errorUploadFileWriter).build());
        return itemWriter;
    }

    @Bean
    @StepScope
    public FlatFileItemReader<CoffeeBean> coffeeUploadFileReader() {
        try {
            FlatFileItemReader<CoffeeBean> reader = new FlatFileItemReader<>();
            reader.setLinesToSkip(1);
            reader.setResource(getResource("coffee.csv"));
            reader.setLineMapper(coffeeLineMapper());
            return reader;
        } catch (Exception e) {
            return null;
        }
    }

    @Bean
    public ValidatingItemProcessor<CoffeeBean> coffeeUploadFileValidateProcessor() {
        return new ValidatingItemProcessor<>(coffeeUploadFileValidator());
    }

    @Bean
    public SpringValidator<CoffeeBean> coffeeUploadFileValidator() {
        SpringValidator validator = new SpringValidator();
        validator.setValidator(coffeeBeanValidator);
        return validator;
    }

    @Bean
    @StepScope
    public CustomLineMapper<CoffeeBean> coffeeLineMapper() {
        CustomLineMapper customLineMapper = new CustomLineMapper();

        Map<String,DelimitedLineTokenizer> delimitedLineTokenizer = new HashMap<>();
        DelimitedLineTokenizer bodyDelimitedLineTokenizer = new DelimitedLineTokenizer();
        bodyDelimitedLineTokenizer.setDelimiter(",");
        bodyDelimitedLineTokenizer.setNames("brand", "origin", "characteristics");
        delimitedLineTokenizer.put("*",bodyDelimitedLineTokenizer);

        Map<String, FieldSetMapper<CoffeeBean>> fieldSetMapperHashMap = new HashMap<>();
        BeanWrapperFieldSetMapper<CoffeeBean> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(CoffeeBean.class);
        fieldSetMapperHashMap.put("*", fieldSetMapper);

        customLineMapper.setTokenizers(delimitedLineTokenizer);
        customLineMapper.setFieldSetMappers(fieldSetMapperHashMap);
        return customLineMapper;
    }

}
