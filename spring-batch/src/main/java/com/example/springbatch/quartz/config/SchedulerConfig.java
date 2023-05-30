package com.example.springbatch.quartz.config;

import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.Properties;

/**
 * @author Nam Tran Date Jul 21, 2017
 */
@Configuration
public class SchedulerConfig {


    @Bean
    public SchedulerFactoryBean scheduler(DataSource  quartzDataSource) throws Exception {
        SchedulerFactoryBean schedulerFactory = new SchedulerFactoryBean();
        schedulerFactory.setConfigLocation(new ClassPathResource("quartz.properties"));
        schedulerFactory.setDataSource(quartzDataSource);
        schedulerFactory.setJobFactory(quartzJobFactory());
        schedulerFactory.setQuartzProperties(quartzProperties());
        schedulerFactory.afterPropertiesSet();
        return schedulerFactory;
    }

    @Bean
    public AutowiringSpringBeanJobFactory quartzJobFactory() {
        return new AutowiringSpringBeanJobFactory();
    }

    @Bean
    public Properties quartzProperties() throws IOException {
        PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
        propertiesFactoryBean.setLocation(new ClassPathResource("quartz.properties"));
        propertiesFactoryBean.afterPropertiesSet();
        return propertiesFactoryBean.getObject();
    }
}
