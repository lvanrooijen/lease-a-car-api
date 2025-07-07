package com.lvr.lease_a_car.entities.car;

import com.lvr.lease_a_car.entities.car.dto.GetCar;
import com.lvr.lease_a_car.entities.car.dto.GetLeaseRate;
import com.lvr.lease_a_car.entities.car.dto.PatchCar;
import com.lvr.lease_a_car.entities.car.dto.PostCar;
import com.lvr.lease_a_car.utils.constants.routes.Routes;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping(Routes.CARS)
@RequiredArgsConstructor
public class CarController {
  private final CarService carService;

  @GetMapping("/{id}")
  public ResponseEntity<GetCar> getCarById(@PathVariable Long id) {
    GetCar car = carService.getCarById(id);
    return ResponseEntity.ok(car);
  }

  @GetMapping()
  public ResponseEntity<List<GetCar>> getAllCars() {
    List<GetCar> cars = carService.getAllCars();
    return ResponseEntity.ok(cars);
  }

  @PostMapping
  public ResponseEntity<GetCar> postCar(@Valid @RequestBody PostCar postCar) {
    GetCar car = carService.createCar(postCar);
    URI location =
        UriComponentsBuilder.newInstance().path("/cars/{id}").buildAndExpand(car.id()).toUri();
    return ResponseEntity.created(location).body(car);
  }

  @PatchMapping("/{id}")
  public ResponseEntity<GetCar> patchCar(@PathVariable Long id, @RequestBody PatchCar patchCar) {
    GetCar car = carService.updateCar(id, patchCar);
    return ResponseEntity.ok(car);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteCar(@PathVariable Long id) {
    carService.deleteCar(id);
    return ResponseEntity.ok().build();
  }

  @GetMapping("/{id}/lease-rate")
  public ResponseEntity<GetLeaseRate> getLeaseRate(
      @PathVariable Long id,
      @RequestParam(required = true) Double duration,
      @RequestParam(required = true) Double interestRate,
      @RequestParam(required = true) Double mileage) {

    GetLeaseRate leaseRate = carService.getLeaseRate(id, duration, interestRate, mileage);
    return ResponseEntity.ok(leaseRate);
  }
}
