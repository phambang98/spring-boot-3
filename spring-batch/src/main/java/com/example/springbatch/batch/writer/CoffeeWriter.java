package com.example.springbatch.batch.writer;

import com.example.springcore.entity.Coffee;
import com.example.springcore.model.CoffeeBean;
import com.example.springcore.repository.CoffeeRepository;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class CoffeeWriter implements ItemWriter<CoffeeBean> {

    Logger logger = LoggerFactory.getLogger(CoffeeWriter.class);
    @Autowired
    private CoffeeRepository coffeeRepository;

    @Override
    public void write(Chunk<? extends CoffeeBean> chunk){
        ModelMapper modelMapper = new ModelMapper();
        List<Coffee> coffeeList = Arrays.asList(modelMapper.map(chunk.getItems(), Coffee[].class));
        coffeeRepository.saveAll(coffeeList);
        logger.info("writer :{},{},{}", chunk.getItems().size(), chunk.getItems().get(0).getBrand(), chunk.getItems().get(chunk.getItems().size() - 1).getBrand());
    }
}
