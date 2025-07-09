package com.lvr.lease_a_car.entities.car;

import com.lvr.lease_a_car.entities.car.dto.GetCar;
import com.lvr.lease_a_car.entities.car.dto.GetLeaseRate;
import com.lvr.lease_a_car.entities.car.dto.PatchCar;
import com.lvr.lease_a_car.entities.car.dto.PostCar;
import com.lvr.lease_a_car.utils.constants.routes.Endpoints;
import com.lvr.lease_a_car.utils.constants.routes.LeaseRateConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

/** Handles http requests related to car */
@Tag(name = "cars", description = "operations related to car management")
@RestController
@RequestMapping(Endpoints.CARS)
@RequiredArgsConstructor
public class CarController {
  private final CarService carService;

  @Operation(
      summary = "Create car",
      description = "creates a new car and stores it in the database")
  @ApiResponse(responseCode = "201", description = "car created, returns body of the created car")
  @ApiResponse(
      responseCode = "400",
      description =
          "car with the same make, model and version is already present in the database, invalid request body provided")
  @PreAuthorize("hasRole('ADMIN')")
  @PostMapping
  public ResponseEntity<GetCar> postCar(@Valid @RequestBody PostCar postCar) {
    GetCar car = carService.createCar(postCar);
    URI location =
        UriComponentsBuilder.newInstance().path("/cars/{id}").buildAndExpand(car.id()).toUri();
    return ResponseEntity.created(location).body(car);
  }

  @Operation(
      summary = "returns lease rate of car",
      description =
          "returns the lease rate of a car given the mileage of the car, the interest rate and the duration of the lease contract")
  @ApiResponse(responseCode = "200", description = "lease rate is calculated")
  @ApiResponse(responseCode = "404", description = "car not found")
  @ApiResponse(responseCode = "400", description = "missing or invalid query parameters")
  @Parameter(name = "id", description = "car ID", required = true)
  @Parameter(
      name = "duration",
      description = "duration of the contract in months",
      allowEmptyValue = false)
  @Parameter(name = "mileage", description = "mileage of car", allowEmptyValue = false)
  @Parameter(name = "interestRate", description = "interest rate", allowEmptyValue = false)
  @PreAuthorize("hasRole('BROKER') or hasRole('ADMIN') or hasRole('EMPLOYEE')")
  @GetMapping("/{id}/lease-rate")
  public ResponseEntity<GetLeaseRate> getLeaseRate(
      @PathVariable Long id,
      @RequestParam(required = true)
          @NotBlank
          @Valid
          @Positive
          @Min(
              value = LeaseRateConstants.MIN_DURATION,
              message = "Duration must be at least " + LeaseRateConstants.MIN_DURATION + " months")
          @Max(
              value = LeaseRateConstants.MAX_DURATION,
              message = "Duration can not exceed " + LeaseRateConstants.MAX_DURATION + " months")
          @Digits(integer = 2, fraction = 0)
          String duration,
      @RequestParam(required = true)
          @NotBlank
          @Positive
          @Max(value = 100, message = "interest rate may not exceed 100%")
          String interestRate,
      @RequestParam(required = true) @NotBlank @Positive String mileage) {

    GetLeaseRate leaseRate = carService.getLeaseRate(id, duration, interestRate, mileage);
    return ResponseEntity.ok(leaseRate);
  }

  @Operation(
      summary = "Get Car by ID",
      description =
          """
                      returns car with the provided ID.
                      If user is an admin, soft-deleted cars are available.
                      Accessible to the following roles: EMPLOYEE, BROKER and ADMIN""")
  @ApiResponse(responseCode = "200", description = "returns car")
  @ApiResponse(responseCode = "404", description = "Car not found")
  @PreAuthorize("hasRole('BROKER') or hasRole('ADMIN') or hasRole('EMPLOYEE')")
  @GetMapping("/{id}")
  public ResponseEntity<GetCar> getCarById(@PathVariable Long id) {
    GetCar car = carService.getCarById(id);
    return ResponseEntity.ok(car);
  }

  @Operation(
      summary = "Get a list of all Cars",
      description =
          "returns a list of all Cars. If user is Admin list contains soft-deleted cars as well Accessible to the following roles: EMPLOYEE, BROKER and ADMIN")
  @ApiResponse(responseCode = "200", description = "returns list of cars")
  @PreAuthorize("hasRole('BROKER') or hasRole('ADMIN') or hasRole('EMPLOYEE')")
  @GetMapping()
  public ResponseEntity<List<GetCar>> getAllCars() {
    List<GetCar> cars = carService.getAllCars();
    return ResponseEntity.ok(cars);
  }

  @Operation(summary = "Update car", description = "updates a car and stores it in the database")
  @ApiResponse(responseCode = "200", description = "car is updated")
  @ApiResponse(responseCode = "404", description = "car does not exist")
  @ApiResponse(responseCode = "400", description = "invalid request body")
  @PreAuthorize("hasRole('ADMIN')")
  @PatchMapping("/{id}")
  public ResponseEntity<GetCar> patchCar(@PathVariable Long id, @RequestBody PatchCar patchCar) {
    GetCar car = carService.updateCar(id, patchCar);
    return ResponseEntity.ok(car);
  }

  @Operation(summary = "delete car", description = "soft-deletes a car")
  @ApiResponse(responseCode = "200", description = "car deleted")
  @ApiResponse(responseCode = "404", description = "car does not exist")
  @PreAuthorize("hasRole('ADMIN')")
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteCar(@PathVariable Long id) {
    carService.deleteCar(id);
    return ResponseEntity.ok().build();
  }
}
