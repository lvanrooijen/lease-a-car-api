package com.lvr.lease_a_car.entities.car.dto;

/**
 * DTO used for patching a Car object
 *
 * <p>Fields may contain null values
 *
 * @param make
 * @param model
 * @param version
 * @param numberOfDoors
 * @param co2Emission
 * @param grossPrice
 * @param nettPrice
 * @param mileage
 */
public record PatchCar(
    String make,
    String model,
    String version,
    Integer numberOfDoors,
    Double co2Emission,
    Double grossPrice,
    Double nettPrice,
    Integer mileage) {}
