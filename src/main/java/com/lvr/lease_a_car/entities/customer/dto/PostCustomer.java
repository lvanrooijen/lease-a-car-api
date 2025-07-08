package com.lvr.lease_a_car.entities.customer.dto;

import com.lvr.lease_a_car.utils.constants.ExceptionMessages;
import jakarta.validation.constraints.*;

/**
 * DTO used for creating Customers
 *
 * <p>All fields are required to contain a value
 *
 * @param name
 * @param street
 * @param houseNumber maximum of 4 (positive) digits
 * @param houseNumberAddition
 * @param zipcode
 * @param city
 * @param email
 * @param phoneNumber
 */
public record PostCustomer(
    @NotBlank String name,
    @NotBlank String street,
    @NotNull
        @Digits(integer = 4, fraction = 0)
        @Positive(message = ExceptionMessages.INVALID_HOUSE_NUMBER)
        String houseNumber,
    @NotBlank String houseNumberAddition,
    @NotBlank String zipcode,
    @NotBlank String city,
    @NotBlank @Email String email,
    @NotBlank String phoneNumber) {}
