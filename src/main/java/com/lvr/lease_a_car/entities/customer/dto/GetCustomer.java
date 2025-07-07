package com.lvr.lease_a_car.entities.customer.dto;

import com.lvr.lease_a_car.entities.customer.Customer;

public record GetCustomer(
    Long id,
    String name,
    String street,
    Integer houseNumber,
    String houseNumberAddition,
    String zipcode,
    String city,
    String email,
    String phoneNumber) {
  public static GetCustomer to(Customer customer) {
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
}
