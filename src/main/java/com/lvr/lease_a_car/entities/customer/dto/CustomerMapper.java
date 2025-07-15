package com.lvr.lease_a_car.entities.customer.dto;

import com.lvr.lease_a_car.entities.customer.Customer;
import org.springframework.stereotype.Component;

/** Class that handles mapping a Customer to a dto, dto to a customer and patching the customer */
@Component
public class CustomerMapper {
  /**
   * Creates a new GetCustomer dto from a {@link Customer} object
   *
   * @param customer represents the customer
   * @return GetCustomer
   */
  public GetCustomer toGetCustomerDto(Customer customer) {
    return new GetCustomer(
        customer.getId(),
        customer.getName(),
        customer.getStreet(),
        customer.getHouseNumber(),
        customer.getHouseNumberAddition(),
        customer.getZipcode(),
        customer.getCity(),
        customer.getEmail(),
        customer.getPhoneNumber());
  }

    /**
     * Creates a new {@link Customer} object from a {@link PostCustomer} object
     *
     * @param dto {@link PostCustomer} dto
     * @return {@link Customer} entity
     */
  public Customer toCustomerEntity(PostCustomer dto) {
      return Customer.builder()
              .name(dto.name())
              .street(dto.street())
              .houseNumber(Integer.parseInt(dto.houseNumber()))
              .houseNumberAddition(dto.houseNumberAddition())
              .zipcode(dto.zipcode())
              .city(dto.city())
              .email(dto.email())
              .phoneNumber(dto.phoneNumber())
              .build();
  }

  /**
   * Returns a {@link Customer} object with updated fields provided by {@link PatchCustomer} object
   *
   * <p>return {@link Customer} to enable method chaining
   *
   * @param customer entity
   * @param patch {@link PatchCustomer}
   * @return {@link Customer} updated customer object
   */
  public Customer updateCustomerFields(Customer customer, PatchCustomer patch) {
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
    return customer;
  }
}
