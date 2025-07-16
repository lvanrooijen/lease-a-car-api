package com.lvr.lease_a_car.entities.car;

import java.math.BigDecimal;
import java.math.RoundingMode;
import org.springframework.stereotype.Service;

@Service
public class LeaseRateService {
  /**
   * Calculates the lease rate of a car
   *
   * <p>Formula: ((( mileage / 12 ) * duration ) / Nett price) + ((( Interest rate / 100 ) *
   * Nettprice) / 12 )
   *
   * @param duration duration of the lease contract
   * @param interestRate interest rate on the lease contract
   * @param mileage mileage a car has on it
   * @param nettPrice nettPrice of the car
   * @return lease rate
   */
  public BigDecimal calculateLeaseRate(
      BigDecimal mileage, BigDecimal duration, BigDecimal interestRate, BigDecimal nettPrice) {

    return mileage
        .divide(new BigDecimal("12"), RoundingMode.HALF_EVEN)
        .multiply(duration)
        .divide(nettPrice, 15, RoundingMode.HALF_EVEN)
        .add(
            interestRate
                .divide(new BigDecimal("100"), 15, RoundingMode.HALF_EVEN)
                .multiply(nettPrice)
                .divide(new BigDecimal("12"), 15, RoundingMode.HALF_EVEN))
        .setScale(2, RoundingMode.HALF_EVEN);
  }
}
