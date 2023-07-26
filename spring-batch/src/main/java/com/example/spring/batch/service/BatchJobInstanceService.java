package com.example.spring.batch.service;

import com.example.core.repository.BatchJobInstanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BatchJobInstanceService {
    @Autowired
    private BatchJobInstanceRepository batchJobInstanceRepository;

    public boolean existsByJobName(String jobName) {
        return batchJobInstanceRepository.existsByJobName(jobName);
    }
}
