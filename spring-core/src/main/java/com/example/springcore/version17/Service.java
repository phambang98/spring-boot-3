package com.example.springcore.version17;

public sealed interface Service permits Car, Truck {

    int getMaxServiceIntervalInMonths();

    static void abc() {
        System.out.println("ok");
    }

    default int getMaxDistanceBetweenServicesInKilometers() {
        return 100000;
    }

}