package com.lvr.lease_a_car.exception.user;

import com.lvr.lease_a_car.exception.base.BaseBadRequestException;

public class InvalidUserRoleException extends BaseBadRequestException {
  public InvalidUserRoleException(String message) {
    super(message);
  }
}
