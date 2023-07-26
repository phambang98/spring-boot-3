package com.example.spring.batch.writer;

import com.example.core.model.BaseBeanBatch;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Component
@StepScope
public class ErrorUploadFileWriter implements ItemWriter<BaseBeanBatch> {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Value("#{jobExecutionContext['jobId']}")
    private Long jobId;

    @Override
    public void write(final Chunk<? extends BaseBeanBatch> chunk) throws Exception {

        String sql = "INSERT INTO UPLOAD_FILE_ERROR(UPLOAD_FILE_ERROR,UPLOAD_FILE_ERROR_ID,LINE_NUMBER,LINE,ERROR_CODE,ERROR_MESSAGE,ETL_DATE, BATCH_NO) "
                + "VALUES (?,?,?,?,?,?,?,?)";

        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {

            @Override
            public void setValues(PreparedStatement ps, int i)
                    throws SQLException {

                BaseBeanBatch beanBatch = chunk.getItems().get(i);
                ps.setString(1, "");
                ps.setString(2, "");
                ps.setInt(3, beanBatch.getLineNumber());
                ps.setString(4, beanBatch.getLine());
                ps.setString(5, beanBatch.getErrorCode());
                ps.setString(6, beanBatch.getErrorMessage());
                ps.setDate(7, new Date(new java.util.Date().getTime()));
                ps.setString(8, "" + jobId);

            }

            @Override
            public int getBatchSize() {
                return chunk.getItems().size();
            }
        });

    }
}
