package com.lvr.lease_a_car.exception.user;

import com.lvr.lease_a_car.exception.base.BaseNotFoundException;

public class UserNotFoundException extends BaseNotFoundException {
  public UserNotFoundException(String message) {
    super(message);
  }
}
