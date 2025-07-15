package com.lvr.lease_a_car.entities.user.dto;

import com.lvr.lease_a_car.entities.customer.dto.PatchCustomer;
import com.lvr.lease_a_car.entities.user.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
  /**
   * Creates a new {@link GetUser} object from a {@link User} object
   *
   * @param user entity
   * @return {@link GetUser} dto
   */
  public GetUser toGetUserDto(User user) {
    return new GetUser(user.getId(), user.getEmail());
  }

  /**
   * @param user entity
   * @param token jwt-token
   * @return {@link GetUserWithJwtToken} dto
   */
  public GetUserWithJwtToken toGetUserWithJwtToken(User user, String token) {
    return new GetUserWithJwtToken(user.getId(), user.getEmail(), token);
  }

  /**
   * Returns a {@link User} object with updated fields provided by {@link PatchUser} object
   *
   * <p>return {@link User} to enable method chaining
   *
   * @param user entity
   * @param patch {@link PatchCustomer}
   * @return {@link User} updated user object
   */
  public User updateUserFields(User user, PatchUser patch) {
    if (patch.firstName() != null) {
      user.setFirstName(patch.firstName());
    }
    if (patch.lastName() != null) {
      user.setLastName(patch.lastName());
    }
    if (patch.email() != null) {
      user.setEmail(patch.email());
    }
    return user;
  }
}
