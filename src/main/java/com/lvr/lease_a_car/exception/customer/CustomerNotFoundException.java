package com.lvr.lease_a_car.exception.customer;

import com.lvr.lease_a_car.exception.base.BaseNotFoundException;

public class CustomerNotFoundException extends BaseNotFoundException {
  public CustomerNotFoundException(String message) {
    super(message);
  }
}
