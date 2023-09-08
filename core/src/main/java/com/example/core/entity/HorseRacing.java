package com.example.core.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "HORSE_RACING")
public class HorseRacing extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "HORSE_RACING_GROUP_ID")
    private Long horseRacingGroupId;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "DISPLAY_NUMBER")
    private Long displayNumber;

    @Column(name = "IMAGE_URL")
    private String imageUrl;

}
