package com.lvr.lease_a_car.exception.user;

import com.lvr.lease_a_car.exception.base.BaseBadRequestException;

public class UserAlreadyRegisteredException extends BaseBadRequestException {
  public UserAlreadyRegisteredException(String message) {
    super(message);
  }
}
