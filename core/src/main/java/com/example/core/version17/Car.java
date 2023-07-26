package com.example.core.version17;

public abstract non-sealed class Car extends Vehicle implements Service{

    private final int numberOfSeats;

    public Car(int numberOfSeats, String registrationNumber) {
        super(registrationNumber);
        this.numberOfSeats = numberOfSeats;
    }

    public int getNumberOfSeats() {
        return numberOfSeats;
    }

    @Override
    public int getMaxDistanceBetweenServicesInKilometers() {
        return 100000;
    }

    @Override
    public int getMaxServiceIntervalInMonths() {
        return 12;
    }

}