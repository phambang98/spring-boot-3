package com.example.spring.batch.writer;

import com.example.core.entity.Coffee;
import com.example.core.model.CoffeeBean;
import com.example.core.repository.CoffeeRepository;
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
    public void write(Chunk<? extends CoffeeBean> chunk) {
        ModelMapper modelMapper = new ModelMapper();
        List<Coffee> coffeeList = Arrays.asList(modelMapper.map(chunk.getItems(), Coffee[].class));
        coffeeList.forEach(x -> x.setCoffeeIdd(coffeeRepository.getSequenceDb("SEQ_Coffee")));
        coffeeRepository.saveAll(coffeeList);
        logger.info("writer :{},{},{}", chunk.getItems().size(), chunk.getItems().get(0).getBrand(), chunk.getItems().get(chunk.getItems().size() - 1).getBrand());
    }


}
