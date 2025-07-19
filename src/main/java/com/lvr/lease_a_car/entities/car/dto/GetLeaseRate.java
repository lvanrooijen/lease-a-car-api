package com.lvr.lease_a_car.entities.car.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;

/**
 * Car DTO containing details returned in a GET lease-rate request
 *
 * <p>Contains a factory method to create a new GetLeaseRate
 *
 * @param carId id of the care the lease rate is from
 * @param leaseRate lease rate
 */
@Schema(description = "Data returned when a lease rate is requested")
public record GetLeaseRate(Long carId, BigDecimal leaseRate) {}
