package com.example.test1_voicu_ionut;

import java.io.Serializable;

public class Car implements Serializable {
    private String make;
    private String model;
    private Integer year;

    public Car(){}

    public Car(String make, String model, Integer year) {
        this.make = make;
        this.model = model;
        this.year = year;
    }

    @Override
    public String toString() {
        return "Car{" +
                "make='" + make + '\'' +
                ", model='" + model + '\'' +
                ", year=" + year +
                '}';
    }
}
