package com.lvr.lease_a_car.entities.car.dto;

import com.lvr.lease_a_car.entities.car.Car;
import org.springframework.stereotype.Component;

/** Class that handles mapping dto's to a car entity, car entity to a dto and patching a car. */
@Component
public class CarMapper {
  /**
   * Creates a new {@link GetCar} object from a {@link Car} entity
   *
   * @param car entity
   * @return {@link GetCar} dto
   */
  public GetCar toGetCarDto(Car car) {
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

  /**
   * Creates a new {@link Car} object from a {@link PostCar} object
   *
   * @param dto {@link PostCar} dto
   * @return {@link Car} entity
   */
  public Car toCarEntity(PostCar dto) {
    return Car.builder()
        .make(dto.make())
        .model(dto.model())
        .version(dto.version())
        .numberOfDoors(dto.numberOfDoors())
        .co2Emission(dto.co2Emission())
        .grossPrice(dto.grossPrice())
        .nettPrice(dto.nettPrice())
        .build();
  }

  /**
   * Returns a {@link Car} object with updated fields provided by {@link PatchCar} object
   *
   * <p>return {@link Car} to enable method chaining
   *
   * @param car entity
   * @param patch {@link PatchCar}
   * @return {@link Car} updated car object
   */
  public Car updateCarFields(Car car, PatchCar patch) {
    if (patch.make() != null) {
      car.setMake(patch.make());
    }
    if (patch.model() != null) {
      car.setModel(patch.model());
    }
    if (patch.version() != null) {
      car.setVersion(patch.version());
    }
    if (patch.numberOfDoors() != null) {
      car.setNumberOfDoors(patch.numberOfDoors());
    }
    if (patch.co2Emission() != null) {
      car.setCo2Emission(patch.co2Emission());
    }
    if (patch.grossPrice() != null) {
      car.setGrossPrice(patch.grossPrice());
    }
    if (patch.nettPrice() != null) {
      car.setNettPrice(patch.nettPrice());
    }
    return car;
  }
}
