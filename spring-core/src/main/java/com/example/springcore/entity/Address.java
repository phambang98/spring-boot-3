package com.example.springcore.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "Address")
public class Address extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "CITY")
    private String city;

    @Column(name = "DISTRICT")
    private String district;

    @Column(name = "ADDRESS_DETAIL")
    private String addressDetail;

    public Long getId() {
        return id;
    }

    public String getCity() {
        return city;
    }

    public String getDistrict() {
        return district;
    }

    public String getAddressDetail() {
        return addressDetail;
    }
}