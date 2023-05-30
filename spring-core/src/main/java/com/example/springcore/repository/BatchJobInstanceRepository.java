package com.example.springcore.repository;

import com.example.springcore.entity.BatchJobInstance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BatchJobInstanceRepository extends JpaRepository<BatchJobInstance, Long> {
    boolean existsByJobName(String jobName);
}
