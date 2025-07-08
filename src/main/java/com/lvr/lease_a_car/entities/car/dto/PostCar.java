package com.lvr.lease_a_car.entities.car.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * DTO used for creating new Cars
 *
 * <p>All fields are required to contain a value
 *
 * @param make
 * @param model
 * @param version
 * @param numberOfDoors
 * @param co2Emission
 * @param grossPrice
 * @param nettPrice
 */
public record PostCar(
    @NotBlank String make,
    @NotBlank String model,
    @NotBlank String version,
    @NotNull Integer numberOfDoors,
    @NotNull Double co2Emission,
    @NotNull Double grossPrice,
    @NotNull Double nettPrice) {}
