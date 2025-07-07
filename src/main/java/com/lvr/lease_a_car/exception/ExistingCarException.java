package com.lvr.lease_a_car.exception;

public class ExistingCarException extends RuntimeException {
  public ExistingCarException(String message) {
    super(message);
  }
}
