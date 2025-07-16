package com.lvr.lease_a_car.exception.car;

import com.lvr.lease_a_car.exception.base.BaseBadRequestException;

public class CarModificationException extends BaseBadRequestException {
  public CarModificationException(String message) {
    super(message);
  }
}
