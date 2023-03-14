package com.example.batch.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.step.skip.SkipLimitExceededException;
import org.springframework.batch.core.step.skip.SkipPolicy;

public class ItemSkipPolicy implements SkipPolicy {

    private int skipLimit;
    Logger logger = LoggerFactory.getLogger(ItemSkipPolicy.class);

    @Override
    public boolean shouldSkip(Throwable throwable, long l) throws SkipLimitExceededException {
        if (throwable instanceof Exception) {
            logger.warn("----------------- Skip customer....:" + throwable, throwable);

            return true;
        } else {
            return false;
        }
    }

    public void setSkipLimit(int skipLimit) {
        this.skipLimit = skipLimit;
    }
}