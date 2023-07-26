package com.example.core.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "UPLOAD_FILE_COFFEE")
public class UploadFileCoffee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "LINE_NUMBER")
    private Integer lineNumber;

    @Column(name = "LINE")
    private String line;

    @Column(name = "BRAND")
    private String brand;
    @Column(name = "ORIGIN")
    private String origin;
    @Column(name = "CHARACTERISTICS")
    private String characteristics;

    @Column(name = "BATCH_NO")
    private String batchNo;

    public UploadFileCoffee() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(Integer lineNumber) {
        this.lineNumber = lineNumber;
    }

    public String getLine() {
        return line;
    }

    public void setLine(String line) {
        this.line = line;
    }

    public Long getCoffeeIdd() {
        return id;
    }

    public void setCoffeeIdd(Long coffeeIdd) {
        this.id = coffeeIdd;
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

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }
}