package com.example.core.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "Coffee")
public class Coffee {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Coffee")
    @SequenceGenerator(sequenceName = "SEQ_Coffee", allocationSize = 1, name = "Coffee")
    @Column(name = "COFFEE_ID")
    private Long coffeeId;

    @Column(name = "BRAND")
    private String brand;
    @Column(name = "ORIGIN")
    private String origin;
    @Column(name = "CHARACTERISTICS")
    private String characteristics;

    public Coffee() {

    }

    public Coffee(String brand, String origin, String characteristics) {
        this.brand = brand;
        this.origin = origin;
        this.characteristics = characteristics;
    }

    public Long getCoffeeIdd() {
        return coffeeId;
    }

    public void setCoffeeIdd(Long coffeeIdd) {
        this.coffeeId = coffeeIdd;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getCharacteristics() {
        return characteristics;
    }

    public void setCharacteristics(String characteristics) {
        this.characteristics = characteristics;
    }
}