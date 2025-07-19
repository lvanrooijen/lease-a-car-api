package com.lvr.lease_a_car.entities.car;

import com.lvr.lease_a_car.entities.car.dto.GetCar;
import com.lvr.lease_a_car.entities.car.dto.GetLeaseRate;
import com.lvr.lease_a_car.entities.car.dto.PatchCar;
import com.lvr.lease_a_car.entities.car.dto.PostCar;
import com.lvr.lease_a_car.utils.constants.routes.Endpoints;
import com.lvr.lease_a_car.utils.constants.routes.LeaseRateConstants;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

/** Handles http requests related to car */
@RestController
@RequestMapping(Endpoints.CARS)
@RequiredArgsConstructor
public class CarController implements CarControllerSwaggerDocumentation {
  private final CarService carService;

  @Override
  @PreAuthorize("hasRole('ADMIN')")
  @PostMapping
  public ResponseEntity<GetCar> postCar(@Valid @RequestBody PostCar postCar) {
    GetCar car = carService.createCar(postCar);
    URI location =
        UriComponentsBuilder.newInstance().path("/cars/{id}").buildAndExpand(car.id()).toUri();
    return ResponseEntity.created(location).body(car);
  }

  @Override
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

  @Override
  @PreAuthorize("hasRole('BROKER') or hasRole('ADMIN') or hasRole('EMPLOYEE')")
  @GetMapping("/{id}")
  public ResponseEntity<GetCar> getCarById(@PathVariable Long id) {
    GetCar car = carService.getCarById(id);
    return ResponseEntity.ok(car);
  }

  @Override
  @PreAuthorize("hasRole('BROKER') or hasRole('ADMIN') or hasRole('EMPLOYEE')")
  @GetMapping()
  public ResponseEntity<List<GetCar>> getAllCars() {
    List<GetCar> cars = carService.getAllCars();
    return ResponseEntity.ok(cars);
  }

  @Override
  @PreAuthorize("hasRole('ADMIN')")
  @PatchMapping("/{id}")
  public ResponseEntity<GetCar> patchCar(@PathVariable Long id, @RequestBody PatchCar patchCar) {
    GetCar car = carService.updateCar(id, patchCar);
    return ResponseEntity.ok(car);
  }

  @Override
  @PreAuthorize("hasRole('ADMIN')")
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteCar(@PathVariable Long id) {
    carService.deleteCar(id);
    return ResponseEntity.ok().build();
  }

  @Override
  @PostMapping(
      value = "upload-cars",
      consumes = {"multipart/form-data"})
  public ResponseEntity<Void> uploadCars(@RequestPart("file") MultipartFile file) {
    carService.uploadCars(file);
    return ResponseEntity.ok().build();
  }
}
