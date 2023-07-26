package com.example.spring.batch.process;

import com.example.core.model.CoffeeBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class CoffeeItemProcessor implements ItemProcessor<CoffeeBean, CoffeeBean> {

    private static final Logger LOGGER = LoggerFactory.getLogger(CoffeeItemProcessor.class);

    @Override
    public CoffeeBean process(final CoffeeBean coffee) {
        LOGGER.info(coffee.getLine());
        return coffee;
    }

}