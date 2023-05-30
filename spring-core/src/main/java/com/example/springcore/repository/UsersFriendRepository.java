package com.example.springcore.repository;


import com.example.springcore.entity.UsersFriend;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UsersFriendRepository extends JpaRepository<UsersFriend, Long> {
}
