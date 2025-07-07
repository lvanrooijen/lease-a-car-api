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

@Service
@RequiredArgsConstructor
public class UserService {
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  public GetUser registerUser(PostUser body) {
    if (userRepository.findByEmailIgnoreCase(body.email()).isPresent()) {
      throw new UserAlreadyRegisteredException("This customer is already registered");
    }

    Role role = null;
    try {
      role = Role.valueOf(body.role());
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

  // TODO mag alleen als user zelf of als juiste rol
  public GetUser updateUser(Long customerId, PatchUser patch) {
    User customer =
        userRepository
            .findById(customerId)
            .orElseThrow(
                () ->
                    new EntityNotFoundException(
                        String.format("No customer with id %d found", customerId)));

    if (patch.firstName() != null) {
      customer.setFirstName(patch.firstName());
    }
    if (patch.lastName() != null) {
      customer.setLastName(patch.lastName());
    }
    if (patch.email() != null) {
      customer.setEmail(patch.email());
    }

    userRepository.save(customer);
    return GetUser.to(customer);
  }

  // TODO mag alleen als user zelf of als juiste rol
  public void deleteUser(Long customerId) {
    userRepository
        .findById(customerId)
        .orElseThrow(
            () ->
                new EntityNotFoundException(
                    String.format(
                        "Can't delete customer, No customer with id %d found", customerId)));
    userRepository.deleteById(customerId);
  }
}
