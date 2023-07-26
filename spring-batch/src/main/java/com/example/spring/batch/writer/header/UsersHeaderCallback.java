package com.example.spring.batch.writer.header;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.file.FlatFileHeaderCallback;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.Writer;

@Component
@StepScope
public class UsersHeaderCallback implements FlatFileHeaderCallback {

    @Override
    public void writeHeader(Writer writer) throws IOException {
        writer.append("ok");
    }
}