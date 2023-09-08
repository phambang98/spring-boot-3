package com.example.core.repository;

import com.example.core.entity.HorseRacingGroup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HorseRacingGroupRepository extends JpaRepository<HorseRacingGroup, Long> {

}
