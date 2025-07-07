package com.lvr.lease_a_car;

import com.lvr.lease_a_car.user.Role;
import com.lvr.lease_a_car.user.User;
import com.lvr.lease_a_car.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Seeder implements CommandLineRunner {
  private final PasswordEncoder passwordEncoder;
  private final UserRepository userRepository;

  @Override
  public void run(String... args) throws Exception {
    seedAdmin();
    seedCustomer();
  }

  private void seedCustomer() {
    User user =
        User.builder()
            .email("customer@email.com")
            .role(Role.CUSTOMER)
            .firstName("cust")
            .lastName("omer")
            .password(passwordEncoder.encode("SecurePassword123!"))
            .build();
    userRepository.save(user);
  }

  private void seedAdmin() {
    User user =
        User.builder()
            .email("admin@email.com")
            .role(Role.ADMIN)
            .firstName("admin")
            .lastName("istrator")
            .password(passwordEncoder.encode("SecurePassword123!"))
            .build();
    userRepository.save(user);
  }
}
