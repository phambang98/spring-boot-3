package com.example.core.utils;

import com.p6spy.engine.logging.Category;
import com.p6spy.engine.spy.appender.Slf4JLogger;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CustomLogger extends Slf4JLogger {
    private Logger log = LoggerFactory.getLogger("p6spy");

    @Override
    public void logSQL(int connectionId, String now, long elapsed, Category category, String prepared, String sql, String url) {
        String msg = this.strategy.formatMessage(connectionId, now, elapsed, category.toString(), prepared, sql, url);
        if (StringUtils.isBlank(msg)) return;
        if (Category.ERROR.equals(category)) {
            this.log.error(msg);
        } else if (Category.WARN.equals(category)) {
            this.log.warn(msg);
        } else if (Category.DEBUG.equals(category)) {
            this.log.debug(msg);
        } else {
            this.log.info(msg);
        }

    }
}
