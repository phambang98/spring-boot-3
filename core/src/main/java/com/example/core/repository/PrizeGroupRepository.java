package com.example.core.repository;

import com.example.core.entity.PrizeGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PrizeGroupRepository extends JpaRepository<PrizeGroup, Long> {

    @Query("select count(1) from PrizeGroup pg where pg.id = ?1 and trunc(pg.dateTime) =trunc(current_date) ")
    Integer countByIdAndDateTime(Long id);

    @Query("select pg from PrizeGroup pg where trunc(pg.dateTime) =trunc(current_date) order by pg.dateTime desc limit 1")
    PrizeGroup findFirstByCurrentDateTime();
}
