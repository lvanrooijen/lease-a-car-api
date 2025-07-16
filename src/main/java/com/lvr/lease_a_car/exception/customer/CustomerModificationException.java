package com.lvr.lease_a_car.exception.customer;

import com.lvr.lease_a_car.exception.base.BaseBadRequestException;

public class CustomerModificationException extends BaseBadRequestException {
  public CustomerModificationException(String message) {
    super(message);
  }
}
