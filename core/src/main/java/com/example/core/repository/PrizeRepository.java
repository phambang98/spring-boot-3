package com.example.core.repository;

import com.example.core.entity.Prizes;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PrizeRepository extends JpaRepository<Prizes, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query(value = "select p " +
            "from Prizes p inner join PrizeGroup pg on p.prizeGroupId = pg.id " +
            "where p.prizeGroupId = :prizeGroupId  and p.luckNumber =:luckNumber")
    Prizes getPrizeGroupIdAndLuckNumber(@Param("prizeGroupId") Long prizeGroupId, @Param("luckNumber") Long luckNumber);

    @Query("select p from Prizes p inner join PrizeGroup pg on p.prizeGroupId = pg.id and pg.id != 2 order by p.displayNumber ")
    List<Prizes> getAllPrizeByPrizeGroupId();


    @Query(value = "select p.* from Prizes p  inner join Prize_Group pg on p.prize_Group_Id = pg.id and pg.id = 2 order by p.display_Number limit :number",nativeQuery = true)
    List<Prizes> getAllPrizeByPrizeGroupIdDefault(@Param("number") int number);
}
