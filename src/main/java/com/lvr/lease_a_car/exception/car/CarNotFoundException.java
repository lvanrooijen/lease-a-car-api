package com.lvr.lease_a_car.exception.car;

import com.lvr.lease_a_car.exception.base.BaseNotFoundException;

public class CarNotFoundException extends BaseNotFoundException {
  public CarNotFoundException(String message) {
    super(message);
  }
}
