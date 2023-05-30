package com.example.springbatch.batch.tasklet;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.jdbc.core.JdbcTemplate;

public class CoffeeRemoveTasklet implements Tasklet {

    private JdbcTemplate jdbcTemplate;

    public CoffeeRemoveTasklet setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        return this;
    }

    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
        JobExecution jobExecution = chunkContext.getStepContext().getStepExecution().getJobExecution();
        Long jobId = jobExecution.getId();
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("delete from upload_file_coffee where batch_no =?");
        jdbcTemplate.update(sqlBuilder.toString(), jobId);
        return RepeatStatus.FINISHED;
    }

    public static CoffeeRemoveTasklet builder() {
        return new CoffeeRemoveTasklet();
    }
}
