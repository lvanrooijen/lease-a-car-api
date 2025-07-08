package com.lvr.lease_a_car.entities.car.dto;

import com.lvr.lease_a_car.entities.car.Car;
import java.math.BigDecimal;

/**
 * Car DTO containing details returned in a GET lease-rate request
 *
 * <p>Contains a factory method to create a new GetLeaseRate
 *
 * @param carId id of the care the lease rate is from
 * @param leaseRate lease rate
 */
public record GetLeaseRate(Long carId, BigDecimal leaseRate) {
  /**
   * Creates a GetLeaseRate DTO to return in HTTP-requests
   *
   * @param leaseRate The lease rate
   * @param car {@link Car} the car the lease-rate is from
   * @return {@link GetLeaseRate}
   */
  public static GetLeaseRate to(BigDecimal leaseRate, Car car) {
    return new GetLeaseRate(car.getId(), leaseRate);
  }
}
