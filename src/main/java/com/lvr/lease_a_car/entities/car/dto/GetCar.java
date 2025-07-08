package com.lvr.lease_a_car.entities.car.dto;

import com.lvr.lease_a_car.entities.car.Car;

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
    Double nettPrice) {
  /**
   * Converts a {@link Car} to a GetCar DTO
   *
   * @param car {@link Car} Car entity to be converted to dto
   * @return GetCar DTO
   */
  public static GetCar to(Car car) {
    return new GetCar(
        car.getId(),
        car.getMake(),
        car.getModel(),
        car.getVersion(),
        car.getNumberOfDoors(),
        car.getCo2Emission(),
        car.getGrossPrice(),
        car.getNettPrice());
  }
}
