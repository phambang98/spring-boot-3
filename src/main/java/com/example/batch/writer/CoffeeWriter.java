package com.example.batch.writer;

import com.example.entity.Coffee;
import com.example.model.CoffeeBean;
import com.example.repository.CoffeeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class CoffeeWriter implements ItemWriter<CoffeeBean> {

    @Autowired
    private CoffeeRepository coffeeRepository;

    @Override
    public void write(Chunk<? extends CoffeeBean> chunk) throws Exception {
        ModelMapper modelMapper = new ModelMapper();
        List<Coffee> coffeeList = Arrays.asList(modelMapper.map(chunk.getItems(), Coffee[].class));
        coffeeRepository.saveAll(coffeeList);
    }
}
