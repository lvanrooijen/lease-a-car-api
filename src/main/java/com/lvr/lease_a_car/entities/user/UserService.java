package com.lvr.lease_a_car.entities.user;

import com.lvr.lease_a_car.entities.user.dto.GetUser;
import com.lvr.lease_a_car.entities.user.dto.PatchUser;
import com.lvr.lease_a_car.entities.user.dto.PostUser;
import com.lvr.lease_a_car.exception.InvalidUserRoleException;
import com.lvr.lease_a_car.exception.UserAlreadyRegisteredException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/** Handles the business logic related to users */
@Service
@RequiredArgsConstructor
public class UserService {
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  /**
   * Creates a user
   *
   * @param body {@link PostUser} DTO
   * @return {@link GetUser}
   * @throws UserAlreadyRegisteredException if a user with this email address is present in the
   *     database
   * @throws InvalidUserRoleException if an invalid user role is passed
   */
  public GetUser registerUser(PostUser body) {
    if (userRepository.findByEmailIgnoreCase(body.email()).isPresent()) {
      throw new UserAlreadyRegisteredException("This customer is already registered");
    }

    Role role = null;
    try {
      role = Role.valueOf(body.role().toUpperCase());
    } catch (IllegalArgumentException e) {
      throw new InvalidUserRoleException("Invalid role");
    }

    User user =
        User.builder()
            .email(body.email())
            .password(passwordEncoder.encode(body.password()))
            .firstName(body.firstName())
            .lastName(body.lastName())
            .role(role)
            .build();

    userRepository.save(user);
    return GetUser.to(user);
  }

  /**
   * Updates a user and saves it in the database
   *
   * @param id represents the user id
   * @param patch {@link PatchUser}
   * @return {@link GetUser}
   * @throws EntityNotFoundException when a user with this ID is not found
   */
  public GetUser updateUser(Long id, PatchUser patch) {
    User user =
        userRepository
            .findById(id)
            .orElseThrow(
                () -> new EntityNotFoundException(String.format("No user with id %d found", id)));

    updateUserFields(user, patch);

    userRepository.save(user);
    return GetUser.to(user);
  }

  /**
   * Deletes a user
   *
   * @param id represents user id
   * @throws EntityNotFoundException when user ID is not present in the database
   */
  public void deleteUser(Long id) {
    userRepository
        .findById(id)
        .orElseThrow(
            () ->
                new EntityNotFoundException(
                    String.format("Can't delete user, user with id %d found", id)));
    userRepository.deleteById(id);
  }

  /**
   * Patches the user object
   *
   * @param user
   * @param patch
   */
  public void updateUserFields(User user, PatchUser patch) {
    if (patch.firstName() != null) {
      user.setFirstName(patch.firstName());
    }
    if (patch.lastName() != null) {
      user.setLastName(patch.lastName());
    }
    if (patch.email() != null) {
      user.setEmail(patch.email());
    }
  }
}
