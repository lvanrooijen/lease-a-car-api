package com.lvr.lease_a_car.entities.customer;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/** Represents a customer of the app */
@Entity
@Getter
@Setter
@Table(name = "customers")
@NoArgsConstructor
public class Customer {
  @Id @GeneratedValue Long id;

  @Column(nullable = false)
  private String name;

  @Column(nullable = false)
  private String street;

  @Column(nullable = false)
  private int houseNumber;

  @Column(nullable = true)
  private String houseNumberAddition; // huisnummer toevoeging

  @Column(nullable = false)
  private String zipcode;

  @Column(nullable = false)
  private String city;

  @Column(nullable = false)
  private String email;

  @Column(nullable = false)
  private String phoneNumber;

  @Builder
  public Customer(
      String name,
      String street,
      int houseNumber,
      String houseNumberAddition,
      String zipcode,
      String city,
      String email,
      String phoneNumber) {
    this.name = name;
    this.street = street;
    this.houseNumber = houseNumber;
    this.houseNumberAddition = houseNumberAddition;
    this.zipcode = zipcode;
    this.city = city;
    this.email = email;
    this.phoneNumber = phoneNumber;
  }
}
