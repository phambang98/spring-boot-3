package com.example.repository;

import com.example.entity.BatchJobInstance;
import com.example.entity.Coffee;
import com.example.model.BaseBeanBatch;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BatchJobInstanceRepository extends JpaRepository<BatchJobInstance, Long> {
    boolean existsByJobName(String jobName);
}
