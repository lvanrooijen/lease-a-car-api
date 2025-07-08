package com.lvr.lease_a_car.entities.user.dto;

import com.lvr.lease_a_car.entities.user.User;

/**
 * * User DTO containing details returned in HTTP-requests * *
 *
 * <p>Contains a factory method that handles creating a new GetUser DTO
 *
 * @param id
 * @param username
 */
public record GetUser(Long id, String username) {
  public static GetUser to(User user) {
    return new GetUser(user.getId(), user.getEmail());
  }
}
