package com.lvr.lease_a_car.entities.customer;

import com.lvr.lease_a_car.entities.customer.dto.GetCustomer;
import com.lvr.lease_a_car.entities.customer.dto.PatchCustomer;
import com.lvr.lease_a_car.entities.customer.dto.PostCustomer;
import com.lvr.lease_a_car.exception.CustomerNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerService {
  private final CustomerRepository customerRepository;

  public GetCustomer createCustomer(PostCustomer body) {
    Customer customer =
        Customer.builder()
            .name(body.name())
            .street(body.street())
            .houseNumber(Integer.parseInt(body.houseNumber()))
            .houseNumberAddition(body.houseNumberAddition())
            .zipcode(body.zipcode())
            .city(body.city())
            .email(body.email())
            .phoneNumber(body.phoneNumber())
            .build();
    customerRepository.save(customer);
    return GetCustomer.to(customer);
  }

  public GetCustomer patchCustomer(Long id, PatchCustomer patch) {
    Customer customer =
        customerRepository
            .findById(id)
            .orElseThrow(() -> new CustomerNotFoundException("Customer does not exist"));

    updateFields(customer, patch);

    customerRepository.save(customer);
    return GetCustomer.to(customer);
  }

  public void deleteCustomer(Long id) {
    customerRepository
        .findById(id)
        .orElseThrow(() -> new CustomerNotFoundException("Customer does not exist"));

    customerRepository.deleteById(id);
  }

  public void updateFields(Customer customer, PatchCustomer patch) {
    if (patch.name() != null) {
      customer.setName(patch.name());
    }
    if (patch.street() != null) {
      customer.setStreet(patch.street());
    }
    if (patch.houseNumber() != null) {
      customer.setHouseNumber(Integer.parseInt(patch.houseNumber()));
    }
    if (patch.houseNumberAddition() != null) {
      customer.setHouseNumberAddition(patch.houseNumberAddition());
    }
    if (patch.zipcode() != null) {
      customer.setZipcode(patch.zipcode());
    }
    if (patch.city() != null) {
      customer.setCity(patch.city());
    }
    if (patch.email() != null) {
      customer.setEmail(patch.email());
    }
    if (patch.phoneNumber() != null) {
      customer.setPhoneNumber(patch.phoneNumber());
    }
  }
}
