package com.example.spring.mq.service;
;
import com.example.spring.mq.model.AbstractBaseRequest;
import com.example.spring.mq.model.AbstractBaseResponse;
import org.springframework.stereotype.Service;

@Service("MQ-01")
public class Mq01Service extends AbstractBaseService {
    @Override
    public AbstractBaseResponse process(AbstractBaseRequest target) {
        StringBuilder errorMessage = new StringBuilder("");
        return null;
    }
}
