package com.lvr.lease_a_car.entities.car.dto;

import java.math.BigDecimal;

public record GetLeaseRate(BigDecimal leaseRate) {
  public static GetLeaseRate to(BigDecimal leaseRate) {
    return new GetLeaseRate(leaseRate);
  }
}
