package com.example.spring.batch.partitioner;

import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CoffeePartitioner implements Partitioner {

    Logger logger = LoggerFactory.getLogger(CoffeePartitioner.class);

    private Long jobId;

    private JdbcTemplate jdbcTemplate;

    @Override
    public Map<String, ExecutionContext> partition(int gridSize) {
        Map<String, ExecutionContext> result = new HashMap<>();

        String sql = "select id from Upload_file_coffee where batch_no ='" + jobId + "'";

        List<String> ranges = jdbcTemplate
                .queryForList(String.format(" SELECT MIN(id) || '-' || MAX(id) FROM ( SELECT ntile(%s) OVER(ORDER BY id) AS PART , id " +
                        "   FROM (%s) GROUP BY id ) TMP GROUP BY PART ORDER BY PART", gridSize, sql), String.class);
        if (CollectionUtils.isNotEmpty(ranges)) {
            for (int i = 0; i < ranges.size(); i++) {
                ExecutionContext value = new ExecutionContext();
                String[] range = ranges.get(i).split("-");
                value.putString("minValue", range[0]);
                value.putString("maxValue", range[1]);
                value.putString("partitionName", String.valueOf(i + 1));
                result.put("partition" + i, value);
            }
        } else {
            logger.warn("------------------- No Coffee to process ---------------------");
        }

        return result;
    }

    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void setJobId(Long jobId) {
        this.jobId = jobId;
    }
}
