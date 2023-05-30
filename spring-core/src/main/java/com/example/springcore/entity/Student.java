package com.example.springcore.entity;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "Student")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    @Column(name = "NAME")
    private String name;
    @Column(name = "ROLL_NUMBER")
    private String rollNumber;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "ID_STUDENT")
    private Set<Bags> bags;

    public Student() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRollNumber() {
        return rollNumber;
    }

    public void setRollNumber(String rollNumber) {
        this.rollNumber = rollNumber;
    }

    public Set<Bags> getBags() {
        return bags;
    }

    public void setBags(Set<Bags> bags) {
        this.bags = bags;
    }
}