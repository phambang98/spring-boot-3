package com.example.ulti;

import com.p6spy.engine.spy.appender.MessageFormattingStrategy;
import lombok.extern.log4j.Log4j2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Log4j2
public class PrettySqlFormat implements MessageFormattingStrategy {
    Logger logger = LoggerFactory.getLogger(MessageFormattingStrategy.class);

    @Override
    public String formatMessage(int connectionId, String now, long elapsed, String category, String prepared, String sql, String url) {

        logger.info(sql);
        return sql;
    }
}