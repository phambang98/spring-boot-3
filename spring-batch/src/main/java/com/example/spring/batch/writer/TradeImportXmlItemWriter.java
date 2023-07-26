package com.example.spring.batch.writer;

import com.example.core.entity.Trade;
import com.example.core.model.TradeBean;
import com.example.core.repository.TradeRepository;
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
public class TradeImportXmlItemWriter implements ItemWriter<TradeBean> {

    Logger logger = LoggerFactory.getLogger(TradeImportXmlItemWriter.class);
    @Autowired
    private TradeRepository tradeRepository;

    @Override
    public void write(Chunk<? extends TradeBean> chunk) {
        ModelMapper modelMapper = new ModelMapper();
        List<Trade> trades = Arrays.asList(modelMapper.map(chunk.getItems(), Trade[].class));
        tradeRepository.saveAll(trades);
//        List<Trade> trades = Arrays.asList(modelMapper.map(chunk.getItems(), Trade[].class));
//        tradeRepository.saveAll(trades);
//        logger.info("writer trade :{},{},{}", chunk.getItems().size(), chunk.getItems().get(0).getTradeBeans(), chunk.getItems().get(chunk.getItems().size() - 1).getTradeBeans());
    }
}
