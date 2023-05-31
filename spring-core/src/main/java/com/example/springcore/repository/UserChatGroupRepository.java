package com.example.springcore.repository;

import com.example.springcore.entity.UserChatGroup;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserChatGroupRepository extends JpaRepository<UserChatGroup, Long> {
}
