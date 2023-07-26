package com.example.spring.batch.job;

import com.example.spring.batch.configuration.BatchConfiguration;
import com.example.core.model.TradeBean;
import com.thoughtworks.xstream.security.AnyTypePermission;
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
import org.springframework.batch.item.xml.StaxEventItemWriter;
import org.springframework.batch.item.xml.builder.StaxEventItemWriterBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.oxm.xstream.XStreamMarshaller;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class TradeExportXmlConfiguration extends BatchConfiguration {

    @Bean
    public Job tradeExportXmlJob(JobRepository jobRepository, @Qualifier("tradeExportXmlStep") Step tradeExportXmlStep) {
        return new JobBuilder("tradeExportXmlJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(tradeExportXmlStep)
                .listener(jobNotificationListener)
                .build();
    }

    @Bean
    public Step tradeExportXmlStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("tradeExportXmlStep", jobRepository)
                .<TradeBean, TradeBean>chunk(chunkSize, transactionManager)
                .reader(tradeExportXmlItemReader())
                .writer(tradeExportXmlItemWriter())
                .faultTolerant()
                .skip(Exception.class)
                .skipPolicy(skipPolicy())
                .listener(stepNotificationListener)
                .build();
    }

    @Bean
    @StepScope
    public JdbcPagingItemReader<TradeBean> tradeExportXmlItemReader() {
        JdbcPagingItemReader<TradeBean> reader = new JdbcPagingItemReader<>();
        reader.setDataSource(this.dataSource);
        reader.setFetchSize(chunkSize);
        reader.setPageSize(chunkSize);
        reader.setRowMapper(new BeanPropertyRowMapper<>(TradeBean.class));

        OraclePagingQueryProvider queryProvider = new OraclePagingQueryProvider();
        queryProvider.setSelectClause("*");
        queryProvider.setFromClause("Trade ");
        Map<String, Order> sortKeys = new HashMap<>();
        sortKeys.put("ID", Order.ASCENDING);
        queryProvider.setSortKeys(sortKeys);
        reader.setQueryProvider(queryProvider);
        reader.setSaveState(false);
        return reader;
    }

    @Bean
    @StepScope
    public StaxEventItemWriter<TradeBean> tradeExportXmlItemWriter() {
        Map<String, Object> aliases = new HashMap<>();
        aliases.put("trade", TradeBean.class);
        XStreamMarshaller xmlMarshaller = new XStreamMarshaller();
        xmlMarshaller.setAliases(aliases);
        xmlMarshaller.setTypePermissions(AnyTypePermission.ANY);
        return new StaxEventItemWriterBuilder<TradeBean>()
                .name("tradeExportXmlItemWriter")
                .marshaller(xmlMarshaller)
                .resource(getPathFileOutput("trade_" + new Date().getTime() + ".xml"))
                .rootTagName("trades")
                .saveState(false)
                .build();
    }

}
