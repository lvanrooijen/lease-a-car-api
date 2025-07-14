package com.lvr.lease_a_car.entities.car.dto;


/**
 * Car DTO containing details returned in HTTP-requests
 *
 * <p>Contains a factory method that handles creating a new GetCar DTO
 *
 * @param id
 * @param make
 * @param model
 * @param version
 * @param numberOfDoors
 * @param co2Emission
 * @param grossPrice
 * @param nettPrice
 */
public record GetCar(
    Long id,
    String make,
    String model,
    String version,
    Integer numberOfDoors,
    Double co2Emission,
    Double grossPrice,
    Double nettPrice) {}
