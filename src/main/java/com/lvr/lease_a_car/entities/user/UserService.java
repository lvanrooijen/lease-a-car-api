package com.lvr.lease_a_car.entities.user;

import com.lvr.lease_a_car.entities.user.dto.*;
import com.lvr.lease_a_car.events.userregistration.UserRegistrationEvent;
import com.lvr.lease_a_car.events.userregistration.UserRegistrationPublisher;
import com.lvr.lease_a_car.exception.FailedLoginException;
import com.lvr.lease_a_car.exception.InvalidUserRoleException;
import com.lvr.lease_a_car.exception.UserAlreadyRegisteredException;
import com.lvr.lease_a_car.security.jwt.JwtService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/** Handles the business logic related to users */
@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;
  private final UserMapper userMapper;
  private final UserRegistrationPublisher eventPublisher;

  /**
   * Creates a user
   *
   * @param body {@link PostUser} DTO
   * @return {@link GetUser}
   * @throws UserAlreadyRegisteredException if a user with this email address is present in the
   *     database
   * @throws InvalidUserRoleException if an invalid user role is passed
   */
  public GetUserWithJwtToken registerUser(PostUser body) {
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

    UserRegistrationEvent userRegistrationEvent =
        new UserRegistrationEvent(user, user.getEmail(), user.getId());
    eventPublisher.publishUserRegistrationEvent(
        userRegistrationEvent.getEmail(), userRegistrationEvent.getUserId());

    String token = jwtService.generateTokenForUser(user);
    return new GetUserWithJwtToken(user.getId(), user.getUsername(), token);
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

    userMapper.updateUserFields(user, patch);
    userRepository.save(user);
    return userMapper.toGetUserDto(user);
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

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return userRepository.findByEmailIgnoreCase(username).orElse(null);
  }

  public GetUserWithJwtToken login(LoginUser requestBody) {
    User user =
        userRepository
            .findByEmailIgnoreCase(requestBody.username())
            .orElseThrow(() -> new UsernameNotFoundException("invalid username and/or password"));

    if (!passwordEncoder.matches(requestBody.password(), user.getPassword())) {
      throw new FailedLoginException("invalid username and/or password");
    }

    String token = jwtService.generateTokenForUser(user);
    return userMapper.toGetUserWithJwtToken(user, token);
  }
}
