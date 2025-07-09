package com.lvr.lease_a_car.entities.car;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.stream.Stream;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/** Test cases for methods of the authentication service class */
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@ExtendWith(MockitoExtension.class)
class CarServiceTest {

  @Mock CarRepository carRepository;

  @InjectMocks private CarService carService;

  /** Test cases for the calculateLeaseRateMethod */
  @Nested
  class CalculateLeaseRateTest {
    @ParameterizedTest
    @MethodSource("getLeaseRateData")
    void should_output_correct_values(
        BigDecimal mileage,
        BigDecimal duration,
        BigDecimal nettPrice,
        BigDecimal interestRate,
        BigDecimal expected) {

      // when
      BigDecimal result = carService.calculateLeaseRate(mileage, duration, interestRate, nettPrice);
      // then
      assertEquals(expected, result);
    }

    static Stream<Arguments> getLeaseRateData() {
      return Stream.of(
          Arguments.of(
              BigDecimal.valueOf(45000),
              BigDecimal.valueOf(60),
              BigDecimal.valueOf(63000),
              BigDecimal.valueOf(4.5),
              BigDecimal.valueOf(239.82)), // 239,8214285714286
          Arguments.of(
              BigDecimal.valueOf(15000),
              BigDecimal.valueOf(24),
              BigDecimal.valueOf(20000),
              BigDecimal.valueOf(5.0),
              BigDecimal.valueOf(84.83)), // 84,83333333333333
          Arguments.of(
              BigDecimal.valueOf(10000),
              BigDecimal.valueOf(36),
              BigDecimal.valueOf(18000),
              BigDecimal.valueOf(3.5),
              BigDecimal.valueOf(54.16)), // 54,16666666666667
          Arguments.of(
              BigDecimal.valueOf(20000),
              BigDecimal.valueOf(12),
              BigDecimal.valueOf(25000),
              BigDecimal.valueOf(6.0),
              new BigDecimal("125.80")), // 125.8
          Arguments.of(
              BigDecimal.valueOf(12000),
              BigDecimal.valueOf(48),
              BigDecimal.valueOf(22000),
              BigDecimal.valueOf(4.25), // 80,09848484848487
              BigDecimal.valueOf(80.09)),
          Arguments.of(
              BigDecimal.valueOf(8000),
              BigDecimal.valueOf(60),
              BigDecimal.valueOf(17500),
              BigDecimal.valueOf(2.75),
              BigDecimal.valueOf(42.38)), // 42,38988095238095
          Arguments.of(
              BigDecimal.valueOf(18000),
              BigDecimal.valueOf(36),
              BigDecimal.valueOf(30000),
              BigDecimal.valueOf(5.5),
              new BigDecimal("139.30") // 139.3
              ));
    }
  
  }
}
