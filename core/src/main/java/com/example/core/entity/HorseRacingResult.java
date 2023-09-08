package com.example.core.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "HORSE_RACING_RESULT")
public class HorseRacingResult extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "HORSE_RACING_ID")
    private Long horseRacingId;

    @Column(name = "TOP")
    private Long top;

}
