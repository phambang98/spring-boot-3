package com.example.spring.batch.job;

import com.example.spring.batch.writer.footer.UsersFooterCallBack;
import com.example.spring.batch.writer.header.UsersHeaderCallback;
import com.example.spring.batch.configuration.BatchConfiguration;
import com.example.core.model.UsersBean;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.Order;
import org.springframework.batch.item.database.support.OraclePagingQueryProvider;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class UsersConfiguration extends BatchConfiguration {


    @Autowired
    private UsersFooterCallBack usersFooterCallBack;

    @Autowired
    private UsersHeaderCallback usersHeaderCallback;


    @Bean
    public Job usersExportJob(JobRepository jobRepository, @Qualifier("usersExportStep") Step usersExportStep) {
        return new JobBuilder("usersExportJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(usersExportStep)
                .listener(jobNotificationListener)
                .build();
    }

    @Bean
    public Step usersExportStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("usersExportStep", jobRepository)
                .<UsersBean, UsersBean>chunk(chunkSize, transactionManager)
                .reader(usersReader())
                .writer(usersWriter())
                .faultTolerant()
                .skip(Exception.class)
                .skipPolicy(skipPolicy())
                .taskExecutor(batchTaskExecutor)
                .listener(stepNotificationListener)
                .build();
    }

    @Bean
    @StepScope
    public JdbcPagingItemReader<UsersBean> usersReader() {
        JdbcPagingItemReader<UsersBean> jdbcPagingItemReader = new JdbcPagingItemReader<>();
        jdbcPagingItemReader.setDataSource(this.dataSource);
        jdbcPagingItemReader.setFetchSize(chunkSize);
        jdbcPagingItemReader.setPageSize(chunkSize);
        jdbcPagingItemReader.setRowMapper(new BeanPropertyRowMapper<>(UsersBean.class));
        OraclePagingQueryProvider queryProvider = new OraclePagingQueryProvider();
        queryProvider.setSelectClause("*");
        queryProvider.setFromClause("users");
        Map<String, Order> sortKeys = new HashMap<>(1);
        sortKeys.put("id", Order.ASCENDING);
        queryProvider.setSortKeys(sortKeys);
        jdbcPagingItemReader.setQueryProvider(queryProvider);
        jdbcPagingItemReader.setSaveState(false);
        return jdbcPagingItemReader;
    }

    @Bean
    @StepScope
    public FlatFileItemWriter<UsersBean> usersWriter() {
        return new FlatFileItemWriterBuilder<UsersBean>()
                .name("chunkUsersWriter")
                .resource(getPathFileOutput("users_" + new Date().getTime() + ".csv"))
                .delimited().delimiter("|").names(new String[]{"id", "userName", "email", "password", "imageUrl"})
                .headerCallback(usersHeaderCallback)
                .footerCallback(usersFooterCallBack)
                .build();
    }
}
