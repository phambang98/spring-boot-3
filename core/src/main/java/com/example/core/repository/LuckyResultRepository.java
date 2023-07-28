package com.example.core.repository;

import com.example.core.entity.LuckyResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LuckyResultRepository extends JpaRepository<LuckyResult, Long> {

    @Query("select count(1) from LuckyResult lr " +
            "inner join Prizes p on p.id=lr.prizeId " +
            "inner join PrizeGroup pg on p.prizeGroupId =pg.id where lr.userId =:userId and pg.id = :prizeGroupId ")
    Integer countByUserIdAndPrizeId(@Param("userId") Long userId, @Param("prizeGroupId") Long prizeGroupId);
}
