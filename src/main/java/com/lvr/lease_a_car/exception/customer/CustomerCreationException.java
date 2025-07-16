package com.lvr.lease_a_car.exception.customer;

import com.lvr.lease_a_car.exception.base.BaseBadRequestException;

public class CustomerCreationException extends BaseBadRequestException {
  public CustomerCreationException(String message) {
    super(message);
  }
}
