package com.lvr.lease_a_car.entities.car;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;

@Entity
@Table(name = "cars")
@Getter
@Setter
@SQLDelete(sql = "UPDATE cars SET is_deleted = true WHERE id=?")
@NoArgsConstructor
public class Car {
  @Id @GeneratedValue private Long id;

  @Column(nullable = false, length = 100)
  private String make;

  @Column(nullable = false, length = 100)
  private String model;

  @Column(nullable = false, length = 100)
  private String version;

  @Column(nullable = false, length = 50)
  private int numberOfDoors;

  @Column(nullable = false)
  private double co2Emission;

  @Column(nullable = false)
  private double grossPrice;

  @Column(nullable = false)
  private double nettPrice;

  @Column(nullable = false)
  private boolean isDeleted;

  @Builder
  public Car(
      String make,
      String model,
      String version,
      int numberOfDoors,
      Double co2Emission,
      Double grossPrice,
      Double nettPrice,
      boolean isDeleted) {
    this.make = make;
    this.model = model;
    this.version = version;
    this.numberOfDoors = numberOfDoors;
    this.co2Emission = co2Emission;
    this.grossPrice = grossPrice;
    this.nettPrice = nettPrice;
    this.isDeleted = isDeleted;
  }
}
