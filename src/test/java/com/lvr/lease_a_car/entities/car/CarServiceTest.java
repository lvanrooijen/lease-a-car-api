package com.lvr.lease_a_car.entities.car;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

/** Test cases for methods of the authentication service class */
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@ExtendWith(MockitoExtension.class)
class CarServiceTest {
  @InjectMocks private CarService carService;

  /** Test cases for the calculateLeaseRateMethod */
  @Nested
  class calculateLeaseRateTest {

    @ParameterizedTest
    @MethodSource("getLeaseRateData")
    void calculate_lease_rate_should_give_correct_values(
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
              BigDecimal.valueOf(239.76)),
          Arguments.of(
              BigDecimal.valueOf(15000),
              BigDecimal.valueOf(24),
              BigDecimal.valueOf(20000),
              BigDecimal.valueOf(5.0),
              BigDecimal.valueOf(84.83)),
          Arguments.of(
              BigDecimal.valueOf(10000),
              BigDecimal.valueOf(36),
              BigDecimal.valueOf(18000),
              BigDecimal.valueOf(3.5),
              BigDecimal.valueOf(54.17)),
          Arguments.of(
              BigDecimal.valueOf(20000),
              BigDecimal.valueOf(12),
              BigDecimal.valueOf(25000),
              BigDecimal.valueOf(6.0),
              BigDecimal.valueOf(125.80)),
          Arguments.of(
              BigDecimal.valueOf(12000),
              BigDecimal.valueOf(48),
              BigDecimal.valueOf(22000),
              BigDecimal.valueOf(4.25),
              BigDecimal.valueOf(80.10).setScale(2, RoundingMode.HALF_EVEN)),
          Arguments.of(
              BigDecimal.valueOf(8000),
              BigDecimal.valueOf(60),
              BigDecimal.valueOf(17500),
              BigDecimal.valueOf(2.75),
              BigDecimal.valueOf(42.39)),
          Arguments.of(
              BigDecimal.valueOf(18000),
              BigDecimal.valueOf(36),
              BigDecimal.valueOf(30000),
              BigDecimal.valueOf(5.5),
              BigDecimal.valueOf(139.30).setScale(2, RoundingMode.HALF_EVEN)));
    }
  }
}
