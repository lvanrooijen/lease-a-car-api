package com.lvr.lease_a_car.entities.car.dto;

import com.lvr.lease_a_car.entities.car.Car;
import java.math.BigDecimal;

public record GetLeaseRate(Long carId, BigDecimal leaseRate) {
  public static GetLeaseRate to(BigDecimal leaseRate, Car car) {
    return new GetLeaseRate(car.getId(), leaseRate);
  }
}
