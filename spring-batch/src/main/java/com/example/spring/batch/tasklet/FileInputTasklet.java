package com.example.spring.batch.tasklet;

import com.example.core.enums.BatchExitStatus;
import com.example.core.utils.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

public class FileInputTasklet implements Tasklet {

    Logger logger = LoggerFactory.getLogger(FileInputTasklet.class);
    private String fileName;

    public FileInputTasklet fileName(String fileName) {
        this.fileName = fileName;
        return this;
    }

    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) {
        if (!FileUtils.existsFile(FileUtils.UPLOAD_BATCH_INPUT + fileName)) {
            chunkContext.getStepContext().getStepExecution().setExitStatus(new ExitStatus(BatchExitStatus.FAILED.name(), "File " + fileName + " not found!"));
            logger.error("File {} not found!", fileName);
        }
        return RepeatStatus.FINISHED;
    }

    public static FileInputTasklet builder() {
        return new FileInputTasklet();
    }
}
