package com.example.core.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "HORSE_RACING_RESULT_USER")
public class HorseRacingResultUser extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "HORSE_RACING_RESULT_ID")
    private Long horseRacingResultId;

    @Column(name = "USER_ID")
    private Long userId;

    @Column(name = "BET_AMOUNT")
    private Double betAmount;

}
