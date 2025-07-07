package com.lvr.lease_a_car.entities.customer.dto;

import jakarta.validation.constraints.Email;

public record PatchCustomer(
    String name,
    String street,
    Integer houseNumber,
    String houseNumberAddition,
    String zipcode,
    String city,
    @Email String email,
    String phoneNumber) {}
