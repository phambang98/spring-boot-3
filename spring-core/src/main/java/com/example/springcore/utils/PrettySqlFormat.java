package com.example.springcore.utils;

import com.p6spy.engine.spy.appender.MessageFormattingStrategy;

public class PrettySqlFormat implements MessageFormattingStrategy {
    @Override
    public String formatMessage(int connectionId, String now, long elapsed, String category, String prepared, String sql, String url) {

        if (sql.contains("BATCH_")) {
            return "";
        }
        return sql;
    }
}