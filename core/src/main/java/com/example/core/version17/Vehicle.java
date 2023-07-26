package com.example.core.version17;

public abstract sealed class Vehicle permits Car, Dog, Truck {

    protected final String registrationNumber;

    public Vehicle(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }
    public abstract void test();

}