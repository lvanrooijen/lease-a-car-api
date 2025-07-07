package com.lvr.lease_a_car.user;

import com.lvr.lease_a_car.exception.UserAlreadyRegisteredException;
import com.lvr.lease_a_car.user.dto.GetCustomer;
import com.lvr.lease_a_car.user.dto.PatchCustomer;
import com.lvr.lease_a_car.user.dto.PostCustomer;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  public GetCustomer registerCustomer(PostCustomer body) {
    if (userRepository.findByEmailIgnoreCase(body.email()).isPresent()) {
      throw new UserAlreadyRegisteredException("This customer is already registered");
    }

    User user =
        User.builder()
            .email(body.email())
            .password(passwordEncoder.encode(body.password()))
            .firstName(body.firstName())
            .lastName(body.lastName())
            .role(Role.CUSTOMER)
            .build();

    userRepository.save(user);
    return GetCustomer.to(user);
  }

  // TODO mag alleen als user zelf of als juiste rol
  public GetCustomer updateCustomer(Long customerId, PatchCustomer patch) {
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
    return GetCustomer.to(customer);
  }

  // TODO mag alleen als user zelf of als juiste rol
  public void deleteCustomer(Long customerId) {
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
