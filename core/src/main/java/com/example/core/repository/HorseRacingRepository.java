package com.example.core.repository;

import com.example.core.entity.HorseRacing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface HorseRacingRepository extends JpaRepository<HorseRacing, Long> {

    @Query("select hr from HorseRacing hr inner join HorseRacingGroup hrg on hr.horseRacingGroupId = hrg.id " +
            "and trunc(hrg.dateTime) = trunc(current_date) order by hr.displayNumber ")
    List<HorseRacing> getByHorseRacingGroupIdAndCurrentDateTime();
}
