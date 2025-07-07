package com.lvr.lease_a_car;

import com.lvr.lease_a_car.entities.user.Role;
import com.lvr.lease_a_car.entities.user.User;
import com.lvr.lease_a_car.entities.user.UserRepository;
import java.util.List;
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
    seedUsers();
  }

  private void seedUsers() {
    User broker =
        User.builder()
            .email("broker@email.com")
            .role(Role.BROKER)
            .firstName("bro")
            .lastName("ker")
            .password(passwordEncoder.encode("SecurePassword123!"))
            .build();

    User employee =
        User.builder()
            .email("employee@email.com")
            .role(Role.EMPLOYEE)
            .firstName("emp")
            .lastName("loyee")
            .password(passwordEncoder.encode("SecurePassword123!"))
            .build();
    userRepository.saveAll(List.of(employee, broker));
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
