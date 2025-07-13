package com.lvr.lease_a_car.entities.car;

import com.opencsv.bean.CsvBindByName;
import lombok.*;

/**
 * Representation of a single row from a CSV file that uploads cars. This class is used to map CSV
 * columns to Java fields.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CarCsvRepresentation {

  @CsvBindByName(column = "make")
  private String make;

  @CsvBindByName(column = "model")
  private String model;

  @CsvBindByName(column = "version")
  private String version;

  @CsvBindByName(column = "#doors")
  private int numberOfDoors;

  @CsvBindByName(column = "gross_price")
  private String grossPrice;

  @CsvBindByName(column = "nett_price")
  private String nettPrice;
}
