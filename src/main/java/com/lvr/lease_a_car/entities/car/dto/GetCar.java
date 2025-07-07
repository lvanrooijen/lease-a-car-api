package com.lvr.lease_a_car.entities.car.dto;

import com.lvr.lease_a_car.entities.car.Car;

public record GetCar(
    Long id,
    String make,
    String model,
    String version,
    Integer numberOfDoors,
    Double co2Emission,
    Double grossPrice,
    Double nettPrice) {
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
