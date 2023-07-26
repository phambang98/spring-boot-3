package com.example.core.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "BAGS")
public class Bags {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    @Column(name = "NAME")
    private String name;
    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "ID_STUDENT")
    private Long idStudent;
    public Bags() {
    }
}