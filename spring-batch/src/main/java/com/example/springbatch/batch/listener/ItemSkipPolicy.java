package com.example.springbatch.batch.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.step.skip.SkipLimitExceededException;
import org.springframework.batch.core.step.skip.SkipPolicy;

public class ItemSkipPolicy implements SkipPolicy {
    Logger logger = LoggerFactory.getLogger(ItemSkipPolicy.class);

    @Override
    public boolean shouldSkip(Throwable throwable, long l) throws SkipLimitExceededException {
        if (throwable instanceof Exception) {
            logger.warn("----------------- Skip customer....:{} ,index :{} ," + throwable, throwable, l);
            logger.error("", throwable);
            return true;
        } else {
            return false;
        }
    }
}