package com.example.springbatch.batch.writer;


import com.example.springcore.model.BaseBeanBatch;
import org.springframework.batch.item.ItemWriter;
import org.springframework.classify.Classifier;

public class CustomItemWriter<T> implements Classifier<BaseBeanBatch, ItemWriter<T>> {

    private ItemWriter<T> success;

    private ItemWriter<T> error;

    @Override
    public ItemWriter<T> classify(BaseBeanBatch baseBeanBatch) {
        if (!baseBeanBatch.isValid()) {
            return error;
        }

        return success;
    }

    public CustomItemWriter build() {
        return this;
    }

    public CustomItemWriter<T> success(ItemWriter itemWriter) {
        success = itemWriter;
        return this;
    }

    public CustomItemWriter<T> error(ItemWriter itemWriter) {
        error = itemWriter;
        return this;
    }

}
