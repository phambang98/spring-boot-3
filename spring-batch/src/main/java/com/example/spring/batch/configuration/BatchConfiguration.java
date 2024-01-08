package com.example.spring.batch.configuration;

import com.example.spring.batch.listener.ItemSkipPolicy;
import com.example.spring.batch.listener.JobNotificationListener;
import com.example.spring.batch.listener.StepNotificationListener;
import com.example.core.utils.FileUtils;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.configuration.support.JobRegistryBeanPostProcessor;
import org.springframework.batch.core.converter.DefaultJobParametersConverter;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.batch.core.launch.support.SimpleJobOperator;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.PathResource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.task.TaskExecutor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import javax.sql.DataSource;

@Configuration
public class BatchConfiguration {

    @Autowired
    @Qualifier("dataSource")
    protected DataSource dataSource;

    @Autowired
    protected JdbcTemplate jdbcTemplate;

    @Autowired
    private ResourceLoader resourceLoader;
    @Autowired
    protected JobNotificationListener jobNotificationListener;

    @Autowired
    protected StepNotificationListener stepNotificationListener;

    @Value("${data.chunk.size}")
    protected int chunkSize;

    @Value("${BATCH_THREAD_MAX_POOL}")
    private int maxPoolSize;

    @Value("${BATCH_THREAD_CORE_POOL}")
    private int corePoolSize;

    @Value("${BATCH_QUEUE_CAPACITY}")
    private int queueCapacity;

    @Value("${taskExecutor.thread.timeout}")
    private int threadTimeOut;

    @Autowired
    protected TaskExecutor batchTaskExecutor;

    @Bean(name = "batchTaskExecutor")
    public TaskExecutor batchTaskExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(corePoolSize);
        taskExecutor.setQueueCapacity(queueCapacity);
        taskExecutor.setMaxPoolSize(maxPoolSize);
        taskExecutor.setKeepAliveSeconds(threadTimeOut);
        taskExecutor.setThreadNamePrefix("batch-task-");
        taskExecutor.afterPropertiesSet();
        return taskExecutor;
    }

    @Bean
    public JobRegistryBeanPostProcessor jobRegistrar(JobRegistry jobRegistry, ApplicationContext applicationContext) throws Exception {
        JobRegistryBeanPostProcessor registrar = new JobRegistryBeanPostProcessor();

        registrar.setJobRegistry(jobRegistry);
        registrar.setBeanFactory(applicationContext.getAutowireCapableBeanFactory());
        registrar.afterPropertiesSet();

        return registrar;
    }


    @Bean
    public JobOperator jobOperator(JobLauncher jobLauncher, JobRepository jobRepository, JobExplorer jobExplorer, JobRegistry jobRegistry) throws Exception {
        SimpleJobOperator simpleJobOperator = new SimpleJobOperator();

        simpleJobOperator.setJobLauncher(jobLauncher);
        simpleJobOperator.setJobParametersConverter(new DefaultJobParametersConverter());
        simpleJobOperator.setJobRepository(jobRepository);
        simpleJobOperator.setJobExplorer(jobExplorer);
        simpleJobOperator.setJobRegistry(jobRegistry);
        simpleJobOperator.afterPropertiesSet();

        return simpleJobOperator;
    }


    @Bean
    public ItemSkipPolicy skipPolicy() {
        ItemSkipPolicy skipPolicy = new ItemSkipPolicy();
        return skipPolicy;
    }


    protected PathResource getPathFileInput(String pathFile) {
        return new PathResource(FileUtils.UPLOAD_BATCH_INPUT + pathFile);
    }

    protected PathResource getPathFileOutput(String pathFile) {
        return new PathResource(FileUtils.UPLOAD_BATCH_OUTPUT + pathFile);
    }

}