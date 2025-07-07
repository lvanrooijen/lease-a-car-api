package com.lvr.lease_a_car.entities.car.dto;

public record PatchCar(
    String make,
    String model,
    String version,
    Integer numberOfDoors,
    Double co2Emission,
    Double grossPrice,
    Double nettPrice,
    Integer mileage) {}
