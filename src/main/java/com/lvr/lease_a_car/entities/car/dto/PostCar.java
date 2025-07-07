package com.lvr.lease_a_car.entities.car.dto;

// TODO add validation
public record PostCar(
    String make,
    String model,
    String version,
    Integer numberOfDoors,
    Double co2Emission,
    Double grossPrice,
    Double nettPrice) {}
