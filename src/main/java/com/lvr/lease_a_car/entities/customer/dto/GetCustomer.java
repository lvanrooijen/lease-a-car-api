package com.lvr.lease_a_car.entities.customer.dto;

import com.lvr.lease_a_car.entities.customer.Customer;

/**
 * DTO for {@link Customer} object, that is used to return in HTTP-requests
 *
 * @param id
 * @param name
 * @param street
 * @param houseNumber
 * @param houseNumberAddition
 * @param zipcode
 * @param city
 * @param email
 * @param phoneNumber
 */
public record GetCustomer(
    Long id,
    String name,
    String street,
    Integer houseNumber,
    String houseNumberAddition,
    String zipcode,
    String city,
    String email,
    String phoneNumber) {}
