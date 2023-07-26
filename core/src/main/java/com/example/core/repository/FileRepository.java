package com.example.core.repository;

import com.example.core.entity.File;
import com.example.core.entity.Users;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import java.util.List;

public interface FileRepository extends JpaRepository<File, Long> {

    List<File> findByMessageId(Long messageId);

    File findByFileName(String fileName);

}
