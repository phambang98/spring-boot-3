package com.example.spring.batch.job;

import com.example.spring.batch.mapper.CustomLineMapper;
import com.example.spring.batch.partitioner.CoffeePartitioner;
import com.example.spring.batch.process.CoffeeItemProcessor;
import com.example.spring.batch.tasklet.CoffeeRemoveTasklet;
import com.example.spring.batch.tasklet.FileInputTasklet;
import com.example.spring.batch.validator.CoffeeBeanValidator;
import com.example.spring.batch.writer.CoffeeWriter;
import com.example.spring.batch.writer.CustomItemWriter;
import com.example.spring.batch.configuration.BatchConfiguration;
import com.example.spring.batch.writer.ErrorUploadFileWriter;
import com.example.core.model.CoffeeBean;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.Order;
import org.springframework.batch.item.database.support.OraclePagingQueryProvider;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.support.ClassifierCompositeItemWriter;
import org.springframework.batch.item.validator.SpringValidator;
import org.springframework.batch.item.validator.ValidatingItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class CoffeeImportCsvConfiguration extends BatchConfiguration {

    @Autowired
    private CoffeeBeanValidator coffeeBeanValidator;

    @Autowired
    private CoffeeWriter coffeeWriter;

    @Autowired
    private CoffeeItemProcessor coffeeItemProcessor;

    @Autowired
    private ErrorUploadFileWriter errorUploadFileWriter;

    @Bean
    public Job coffeeImportCsvJob(JobRepository jobRepository, @Qualifier("checkExistsFileCoffeeStep") Step checkExistsFileCoffeeStep,
                         @Qualifier("coffeeUploadFileStep") Step coffeeUploadFileStep,
                         @Qualifier("coffeePreStep") Step coffeePreStep,
                         @Qualifier("removeCoffeeUploadFileStep") Step removeCoffeeUploadFileStep) {
        return new JobBuilder("coffeeJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(checkExistsFileCoffeeStep).on("FAILED").end()
                .from(checkExistsFileCoffeeStep).on("COMPLETED")
                .to(coffeeUploadFileStep)
                .next(coffeePreStep)
                .next(removeCoffeeUploadFileStep)
                .end()
                .listener(jobNotificationListener)
                .build();
    }

    @Bean
    public Step checkExistsFileCoffeeStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("checkExistsFileCoffeeStep", jobRepository)
                .tasklet(FileInputTasklet.builder().fileName("coffee.csv"), transactionManager)
                .listener(stepNotificationListener)
                .build();
    }

    @Bean
    public Step removeCoffeeUploadFileStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("removeCoffeeUploadFileStep", jobRepository)
                .tasklet(CoffeeRemoveTasklet.
                        builder().
                        setJdbcTemplate(new JdbcTemplate(dataSource)), transactionManager)
                .listener(stepNotificationListener)
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
                .skipPolicy(skipPolicy())
                .listener(stepNotificationListener)
                .build();
    }

    @Bean
    @StepScope
    public FlatFileItemReader<CoffeeBean> coffeeUploadFileReader() {
        try {
            FlatFileItemReader<CoffeeBean> reader = new FlatFileItemReader<>();
            reader.setLinesToSkip(1);
            reader.setResource(getPathFileInput("coffee.csv"));
            reader.setLineMapper(coffeeLineMapper());
            reader.setSaveState(false);
            return reader;
        } catch (Exception e) {
            return null;
        }
    }

    @Bean
    @StepScope
    public CustomLineMapper<CoffeeBean> coffeeLineMapper() {
        CustomLineMapper customLineMapper = new CustomLineMapper();

        Map<String, DelimitedLineTokenizer> delimitedLineTokenizer = new HashMap<>();
        DelimitedLineTokenizer bodyDelimitedLineTokenizer = new DelimitedLineTokenizer();
        bodyDelimitedLineTokenizer.setDelimiter(",");
        bodyDelimitedLineTokenizer.setNames("brand", "origin", "characteristics");
        delimitedLineTokenizer.put("*", bodyDelimitedLineTokenizer);

        Map<String, FieldSetMapper<CoffeeBean>> fieldSetMapperHashMap = new HashMap<>();
        BeanWrapperFieldSetMapper<CoffeeBean> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(CoffeeBean.class);
        fieldSetMapperHashMap.put("*", fieldSetMapper);

        customLineMapper.setTokenizers(delimitedLineTokenizer);
        customLineMapper.setFieldSetMappers(fieldSetMapperHashMap);
        return customLineMapper;
    }

    @Bean
    public ClassifierCompositeItemWriter<CoffeeBean> classifierCoffeeBeanUploadWriter() {
        ClassifierCompositeItemWriter<CoffeeBean> itemWriter = new ClassifierCompositeItemWriter<>();
        itemWriter.setClassifier(new CustomItemWriter<>().success(jdbcCoffeeUploadFileWriter(null)).error(errorUploadFileWriter).build());
        return itemWriter;
    }

    @Bean
    @StepScope
    public JdbcBatchItemWriter<CoffeeBean> jdbcCoffeeUploadFileWriter(@Value("#{jobExecutionContext['jobId']}") Long jobId) {
        JdbcBatchItemWriter<CoffeeBean> itemWriter = new JdbcBatchItemWriter<>();
        itemWriter.setDataSource(this.dataSource);
        itemWriter.setSql("insert into upload_file_coffee (LINE_NUMBER,LINE,BRAND,ORIGIN,CHARACTERISTICS,BATCH_NO)" +
                "  values (:lineNumber,:line,:brand,:origin,:characteristics,:batchNo)");
        itemWriter.setItemSqlParameterSourceProvider(coffeeBean -> {
//            BeanPropertySqlParameterSource beanPropertySqlParameterSource = new BeanPropertySqlParameterSource(coffeeBean);
//            return beanPropertySqlParameterSource;
            Map<String, Object> params = new HashMap<>();
            params.put("lineNumber", coffeeBean.getLineNumber());
            params.put("line", coffeeBean.getLine());
            params.put("brand", coffeeBean.getBrand());
            params.put("origin", coffeeBean.getOrigin());
            params.put("characteristics", coffeeBean.getCharacteristics());
            params.put("batchNo", jobId);
            return new MapSqlParameterSource(params);
        });
        itemWriter.afterPropertiesSet();
        return itemWriter;
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
    public Step coffeePreStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("coffeePreStep", jobRepository)
                .partitioner(coffeeNextStep(jobRepository, transactionManager).getName(), coffeePartitioner(null))
                .step(coffeeNextStep(jobRepository, transactionManager))
                .gridSize(3)
                .taskExecutor(batchTaskExecutor)
                .build();
    }

    @Bean
    public Step coffeeNextStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("coffeeNextStep", jobRepository)
                .<CoffeeBean, CoffeeBean>chunk(chunkSize, transactionManager)
                .reader(coffeePreReader(null, null, null))
                .processor(coffeeItemProcessor)
                .writer(coffeeWriter)
                .faultTolerant()
                .skip(Exception.class)
                .skipPolicy(skipPolicy())
                .listener(stepNotificationListener)
                .build();
    }

    @Bean
    @StepScope
    public CoffeePartitioner coffeePartitioner(@Value("#{jobExecutionContext['jobId']}") Long jobId) {
        CoffeePartitioner partitioner = new CoffeePartitioner();
        partitioner.setDataSource(this.dataSource);
        partitioner.setJobId(jobId);
        return partitioner;
    }


    @Bean
    @StepScope
    public JdbcPagingItemReader<CoffeeBean> coffeePreReader(@Value("#{stepExecutionContext['minValue']}") String minValue,
                                                            @Value("#{stepExecutionContext['maxValue']}") String maxValue,
                                                            @Value("#{jobExecutionContext['jobId']}") Long jobId) {
        JdbcPagingItemReader<CoffeeBean> jdbcPagingItemReader = new JdbcPagingItemReader<>();
        jdbcPagingItemReader.setDataSource(this.dataSource);
        jdbcPagingItemReader.setFetchSize(chunkSize);
        jdbcPagingItemReader.setPageSize(chunkSize);
        jdbcPagingItemReader.setRowMapper(new BeanPropertyRowMapper<>(CoffeeBean.class));
        OraclePagingQueryProvider queryProvider = new OraclePagingQueryProvider();
        queryProvider.setSelectClause("*");
        queryProvider.setFromClause("UPLOAD_FILE_COFFEE");
        queryProvider.setWhereClause("batch_no ='" + jobId + "' and id >=" + minValue + " and id <=" + maxValue);
        Map<String, Order> sortKeys = new HashMap<>(1);
        sortKeys.put("id", Order.ASCENDING);
        queryProvider.setSortKeys(sortKeys);
        jdbcPagingItemReader.setQueryProvider(queryProvider);
        jdbcPagingItemReader.setSaveState(false);
        return jdbcPagingItemReader;
    }

}
