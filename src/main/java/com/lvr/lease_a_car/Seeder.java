package com.lvr.lease_a_car;

import com.lvr.lease_a_car.entities.car.Car;
import com.lvr.lease_a_car.entities.car.CarRepository;
import com.lvr.lease_a_car.entities.customer.Customer;
import com.lvr.lease_a_car.entities.customer.CustomerRepository;
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
  private final CarRepository carRepository;
  private final CustomerRepository customerRepository;

  @Override
  public void run(String... args) throws Exception {
    seedUsers();
    seedCars();
    seedCustomers();
  }

  private void seedCustomers() {
    if (!customerRepository.findAll().isEmpty()) return;

    Customer scooter =
        Customer.builder()
            .name("Scooter")
            .email("scooter@pandora.com")
            .street("Gearbox Avenue")
            .houseNumber(66)
            .zipcode("1234SC")
            .city("Sanctuary")
            .phoneNumber("+31610001234")
            .build();

    Customer roxxy =
        Customer.builder()
            .name("Roxxy")
            .email("roxxy@pandora.com")
            .street("Garage Street")
            .houseNumber(7)
            .houseNumberAddition("A")
            .zipcode("1234RX")
            .city("Sanctuary")
            .phoneNumber("+31620001234")
            .build();

    customerRepository.saveAll(List.of(scooter, roxxy));
  }

  private void seedCars() {
    if (!carRepository.findAll().isEmpty()) return;

    Car citroen2cv =
        Car.builder()
            .make("Citroën")
            .model("2CV")
            .version("6 Club Cabriolet")
            .numberOfDoors(2)
            .nettPrice(12500.00)
            .grossPrice(15125.00)
            .co2Emission(115.0)
            .isDeleted(false)
            .build();

    Car hondaCivic =
        Car.builder()
            .make("Honda")
            .model("Civic")
            .version("1.5 VTEC Turbo Executive")
            .numberOfDoors(4)
            .nettPrice(28900.00)
            .grossPrice(34989.00)
            .co2Emission(129.0)
            .isDeleted(false)
            .build();
    carRepository.saveAll(List.of(citroen2cv, hondaCivic));
  }

  private void seedUsers() {
    if (!userRepository.findAll().isEmpty()) return;

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

    seedAdmin();
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
