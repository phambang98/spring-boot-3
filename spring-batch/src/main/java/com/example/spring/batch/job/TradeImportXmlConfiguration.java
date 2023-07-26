package com.example.spring.batch.job;

import com.example.spring.batch.mapper.TradeConverter;
import com.example.spring.batch.configuration.BatchConfiguration;
import com.example.spring.batch.tasklet.FileInputTasklet;
import com.example.core.model.TradeBean;
import com.thoughtworks.xstream.security.AnyTypePermission;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.xml.StaxEventItemReader;
import org.springframework.batch.item.xml.builder.StaxEventItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.xstream.XStreamMarshaller;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.HashMap;
import java.util.Map;


@Configuration
public class TradeImportXmlConfiguration extends BatchConfiguration {

    @Bean
    public Job tradeImportXmlJob(JobRepository jobRepository, @Qualifier("checkExistsFileTradeStep") Step checkExistsFileTradeStep,
                                 @Qualifier("tradeImportXmlStep") Step tradeImportXmlStep) {
        return new JobBuilder("tradeImportXmlJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(checkExistsFileTradeStep).on("FAILED").end()
                .from(checkExistsFileTradeStep).on("COMPLETED")
                .to(tradeImportXmlStep).end()
                .listener(jobNotificationListener)
                .build();
    }

    @Bean
    public Step checkExistsFileTradeStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("checkExistsFileTradeStep", jobRepository)
                .tasklet(FileInputTasklet.builder().fileName("trade.xml"), transactionManager)
                .listener(stepNotificationListener)
                .build();
    }

    @Bean
    public Step tradeImportXmlStep(JobRepository jobRepository, PlatformTransactionManager transactionManager,
                                   @Qualifier("tradeImportXmlItemWriter") ItemWriter<TradeBean> tradeImportXmlItemWriter) {
        return new StepBuilder("tradeImportXmlStep", jobRepository)
                .<TradeBean, TradeBean>chunk(chunkSize, transactionManager)
                .reader(tradeImportXmlItemReader())
                .writer(tradeImportXmlItemWriter)
                .faultTolerant()
                .skip(Exception.class)
                .skipPolicy(skipPolicy())
                .listener(stepNotificationListener)
                .build();
    }

    @Autowired
    private TradeConverter tradeConverter;

    @Bean
    @StepScope
    public StaxEventItemReader<TradeBean> tradeImportXmlItemReader() {
        Map<String, Object> aliases = new HashMap<>();
        aliases.put("trade", TradeBean.class);
        XStreamMarshaller xmlMarshaller = new XStreamMarshaller();
        xmlMarshaller.setAliases(aliases);
        xmlMarshaller.setTypePermissions(AnyTypePermission.ANY);
        return new StaxEventItemReaderBuilder<TradeBean>()
                .name("tradeXmlItemReader")
                .resource(getPathFileInput("trade.xml"))
                .addFragmentRootElements("trade")
                .unmarshaller(xmlMarshaller)
                .saveState(false)
                .build();
    }

}
