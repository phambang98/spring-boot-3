package com.example.springmq.service;

import com.example.springmq.model.AbstractBaseRequest;
import com.example.springmq.model.AbstractBaseResponse;
import org.springframework.stereotype.Service;

@Service("MQ-01")
public class Mq01Service extends AbstractBaseService {
    @Override
    public AbstractBaseResponse process(AbstractBaseRequest target) {
        StringBuilder errorMessage = new StringBuilder("");
        return null;
    }
}
