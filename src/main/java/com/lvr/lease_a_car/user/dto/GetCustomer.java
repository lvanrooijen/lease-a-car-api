package com.lvr.lease_a_car.user.dto;

import com.lvr.lease_a_car.user.User;

public record GetCustomer(Long id, String username) {
  public static GetCustomer to(User user) {
    return new GetCustomer(user.getId(), user.getEmail());
  }
}
