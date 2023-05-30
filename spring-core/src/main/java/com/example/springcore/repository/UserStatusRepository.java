package com.example.springcore.repository;

import com.example.springcore.entity.UserStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface UserStatusRepository extends JpaRepository<UserStatus, Long> {

    @Modifying
    @Transactional
    @Query("update UserStatus set status =:status,lastTimeLogin =:lastTimeLogin where userId = :userId")
    void updateStatusAndTimeByUserName(@Param("status") String status, @Param("lastTimeLogin") String lastTimeLogin, @Param("userId") Long userId);

    UserStatus findByUserId(Long userId);

    boolean existsByUserIdAndStatus(Long userId, String status);
}
