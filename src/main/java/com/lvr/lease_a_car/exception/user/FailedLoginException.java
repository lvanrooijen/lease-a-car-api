package com.lvr.lease_a_car.exception.user;


public class FailedLoginException extends RuntimeException {
  public FailedLoginException(String message) {
    super(message);
  }
}
