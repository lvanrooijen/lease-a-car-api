package com.lvr.lease_a_car.entities.user.dto;

import com.lvr.lease_a_car.entities.user.User;

public record GetUser(Long id, String username) {
  public static GetUser to(User user) {
    return new GetUser(user.getId(), user.getEmail());
  }
}
