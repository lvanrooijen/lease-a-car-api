package com.lvr.lease_a_car.exception.generic;

import com.lvr.lease_a_car.exception.base.BaseBadRequestException;

public class InvalidInputException extends BaseBadRequestException {
  public InvalidInputException(String message) {
    super(message);
  }
}
