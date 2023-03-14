package com.example.batch.configuration;

import com.example.batch.listener.ItemSkipPolicy;
import com.example.batch.listener.JobCompletionNotificationListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import javax.sql.DataSource;

@Configuration
public class BatchConfiguration  {

    @Autowired
    @Qualifier("dataSource")
    protected DataSource dataSource;

    @Autowired
    private ResourceLoader resourceLoader;
    @Autowired
    protected JobCompletionNotificationListener jobCompletionNotificationListener;

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

    public Resource getResource(String name) {
        return  resourceLoader.getResource("classpath:batch/" + name);
    }

    @Bean
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
    public ItemSkipPolicy skipPolicy() {
        ItemSkipPolicy skipPolicy = new ItemSkipPolicy();
        skipPolicy.setSkipLimit(100);
        return skipPolicy;
    }


}