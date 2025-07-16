package com.lvr.lease_a_car.exception.car;

import com.lvr.lease_a_car.exception.base.BaseBadRequestException;

public class CarCreationException extends BaseBadRequestException {
  public CarCreationException(String message) {
    super(message);
  }
}
