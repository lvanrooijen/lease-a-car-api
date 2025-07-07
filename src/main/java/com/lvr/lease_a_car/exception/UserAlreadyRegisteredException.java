package com.lvr.lease_a_car.exception;

public class UserAlreadyRegisteredException extends RuntimeException {
  public UserAlreadyRegisteredException(String message) {
    super(message);
  }
}
