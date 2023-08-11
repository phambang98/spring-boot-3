package com.example.spring.batch.tasklet;

import com.example.core.enums.SocketType;
import com.example.core.utils.WebSocketKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

public class PersonTasklet {

    static Logger logger = LoggerFactory.getLogger(PersonTasklet.class);

    public static class PersonTasklet1 implements Tasklet {

        @Override
        public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
            logger.info("tasklet 1");

            return RepeatStatus.FINISHED;
        }

        public static PersonTasklet1 builder() {
            return new PersonTasklet1();
        }
    }

    public static class PersonTasklet11 implements Tasklet {

        @Override
        public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
            logger.info("tasklet 11");
            return RepeatStatus.FINISHED;
        }

        public static PersonTasklet11 builder() {
            return new PersonTasklet11();
        }
    }

    public static class PersonTasklet2 implements Tasklet {

        @Override
        public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
            logger.info("tasklet 2");
            chunkContext.getStepContext().getStepExecution().setExitStatus(new ExitStatus("FAILED", "current cutoff time invalid!"));
            return RepeatStatus.FINISHED;
        }

        public static PersonTasklet2 builder() {
            return new PersonTasklet2();
        }
    }

    public static class PersonTasklet3 implements Tasklet {

        @Override
        public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
            logger.info("tasklet 3");
            return RepeatStatus.FINISHED;
        }

        public static PersonTasklet3 builder() {
            return new PersonTasklet3();
        }
    }
}
