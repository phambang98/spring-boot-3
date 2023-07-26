package com.example.core.repository;

import com.example.core.entity.BatchJobInstance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BatchJobInstanceRepository extends JpaRepository<BatchJobInstance, Long> {
    boolean existsByJobName(String jobName);
}
