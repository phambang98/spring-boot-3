package com.example.core.repository;

import com.example.core.entity.HorseRacingResultUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface HorseRacingResultUserRepository extends JpaRepository<HorseRacingResultUser, Long> {

    @Query(value = "select a from HorseRacingResultUser a where a.userId = ?#{principal.id}")
    HorseRacingResultUser findByUserId(Long userId);
}
