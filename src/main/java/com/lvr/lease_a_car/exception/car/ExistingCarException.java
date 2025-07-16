package com.lvr.lease_a_car.exception.car;

import com.lvr.lease_a_car.exception.base.BaseBadRequestException;

public class ExistingCarException extends BaseBadRequestException {
  public ExistingCarException(String message) {
    super(message);
  }
}
