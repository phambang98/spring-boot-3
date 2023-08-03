package com.example.core.repository;

import com.example.core.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    @Modifying
    int deleteByUserId(Long userId);

    RefreshToken findByRefreshToken(String refreshToken);

    RefreshToken findByUserId(Long userId);

}
