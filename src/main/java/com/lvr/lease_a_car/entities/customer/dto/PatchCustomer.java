package com.lvr.lease_a_car.entities.customer.dto;

import com.lvr.lease_a_car.utils.constants.ExceptionMessages;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Positive;

/**
 * DTO used for patching a customer object
 *
 * <p>Fields may contain null values
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
public record PatchCustomer(
    String name,
    String street,
    @Digits(integer = 4, fraction = 0) @Positive(message = ExceptionMessages.INVALID_HOUSE_NUMBER)
        String houseNumber,
    String houseNumberAddition,
    String zipcode,
    String city,
    @Email String email,
    String phoneNumber) {}
