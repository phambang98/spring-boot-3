package com.example.core.repository;


import com.example.core.entity.UsersFriend;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UsersFriendRepository extends JpaRepository<UsersFriend, Long> {
}
