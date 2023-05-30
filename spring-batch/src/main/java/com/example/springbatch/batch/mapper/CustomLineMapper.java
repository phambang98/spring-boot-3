package com.example.springbatch.batch.mapper;

import com.example.springcore.model.BaseBeanBatch;
import org.springframework.batch.item.file.mapping.PatternMatchingCompositeLineMapper;

public class CustomLineMapper<T extends BaseBeanBatch> extends PatternMatchingCompositeLineMapper<T> {
    @Override
    public T mapLine(String line, int lineNumber) throws Exception {
        T beanBatch;
        beanBatch = super.mapLine(line, lineNumber);
        beanBatch.setLine(line);
        beanBatch.setLineNumber(lineNumber);
        return beanBatch;
    }
}
