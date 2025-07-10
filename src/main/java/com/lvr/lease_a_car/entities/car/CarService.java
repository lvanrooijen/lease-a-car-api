package com.lvr.lease_a_car.entities.car;

import com.lvr.lease_a_car.entities.car.dto.GetCar;
import com.lvr.lease_a_car.entities.car.dto.GetLeaseRate;
import com.lvr.lease_a_car.entities.car.dto.PatchCar;
import com.lvr.lease_a_car.entities.car.dto.PostCar;
import com.lvr.lease_a_car.entities.user.User;
import com.lvr.lease_a_car.exception.ExistingCarException;
import jakarta.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/** Handles the business logic related to cars */
@Service
@RequiredArgsConstructor
public class CarService {
  private final CarRepository carRepository;

  /**
   * Creates a Car
   *
   * <p>Creates a car object and saves it in the database
   *
   * @param postCar {@link PostCar}
   * @return GetCar {@link GetCar}
   * @throws ExistingCarException when a car with this ID is not present in the database
   */
  public GetCar createCar(PostCar postCar) {
    if (isRegisteredMakeModelVersion(postCar.make(), postCar.model(), postCar.version())) {
      throw new ExistingCarException(
          "A car with this make, model and version is already present in the database");
    }
    Car car =
        Car.builder()
            .model(postCar.model())
            .make(postCar.make())
            .co2Emission(postCar.co2Emission())
            .grossPrice(postCar.grossPrice())
            .nettPrice(postCar.nettPrice())
            .numberOfDoors(postCar.numberOfDoors())
            .version(postCar.version())
            .build();

    carRepository.save(car);
    return GetCar.to(car);
  }

  /**
   * Retrieves a car by ID
   *
   * <p>If the authenticated user has admin role, this also returns soft-deleted cars
   *
   * @param id represents the id of the car to retrieve
   * @return {@link GetCar}
   * @throws EntityNotFoundException when a car with this ID is not present in the database
   */
  public GetCar getCarById(Long id) {
    User loggedInUser = getLoggedInUser();

    Car car;
    if (loggedInUser.isAdmin()) {
      car =
          carRepository
              .findById(id)
              .orElseThrow(
                  () -> new EntityNotFoundException("Car by id " + id + " can not be found"));
    } else {
      car =
          carRepository
              .findByIdAndIsDeletedFalse(id)
              .orElseThrow(
                  () -> new EntityNotFoundException("Car by id " + id + " can not be found"));
    }
    return GetCar.to(car);
  }

  /**
   * Retrieves a list of cars
   *
   * <p>If the authenticated user had admin role, the returned list will contain soft deleted cars
   *
   * @return list of {@link GetCar}
   */
  public List<GetCar> getAllCars() {
    User loggedInUser = getLoggedInUser();

    List<Car> cars;
    if (loggedInUser.isAdmin()) {
      cars = carRepository.findAll();
    } else {
      cars = carRepository.findAllByIsDeletedFalse();
    }
    return cars.stream().map(GetCar::to).toList();
  }

  /**
   * Returns lease rate
   *
   * @param carId represent the ID of the car
   * @param duration duration of the lease contract
   * @param interestRate interest rate on the lease contract
   * @param mileage mileage a car has on it
   * @return {@link GetLeaseRate}
   * @throws EntityNotFoundException when a car with this ID is not present in the database
   */
  public GetLeaseRate getLeaseRate(
      Long carId, String duration, String interestRate, String mileage) {

    Car car =
        carRepository
            .findById(carId)
            .orElseThrow(
                () -> new EntityNotFoundException("Car with id " + carId + " can not be found"));

    BigDecimal leaseRate =
        calculateLeaseRate(
            new BigDecimal(mileage),
            new BigDecimal(duration),
            new BigDecimal(interestRate),
            BigDecimal.valueOf(car.getNettPrice()));

    return GetLeaseRate.to(leaseRate, car);
  }

  /**
   * Updates a car and saves it in the database
   *
   * @param id represents the ID of the car
   * @param patch {@link PatchCar}
   * @return {@link GetCar} updated car
   * @throws EntityNotFoundException when a car with this ID is not present in the database
   */
  public GetCar updateCar(Long id, PatchCar patch) {
    Car car =
        carRepository
            .findById(id)
            .orElseThrow(
                () ->
                    new EntityNotFoundException(
                        String.format(
                            "Failed to update car, Car with id %d can not be found", id)));

    updateCarFields(car, patch);
    carRepository.save(car);
    return GetCar.to(car);
  }

  /**
   * Soft-deletes a car
   *
   * <p>The field isDeleted of {@link Car} will be set to true when deleted
   *
   * @param id represents the id of the car
   * @throws EntityNotFoundException when a car with this ID is not present in the database
   */
  public void deleteCar(Long id) {
    Car car =
        carRepository
            .findById(id)
            .orElseThrow(
                () ->
                    new EntityNotFoundException(
                        String.format("Failed to delete, a car with id %d can not be found.", id)));
    car.setDeleted(true);
    carRepository.save(car);
  }

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

  /**
   * Checks if a car with the same make, model and version is present in the database
   *
   * <p>Does not take soft-deleted cars into consideration
   *
   * @param make represents the make of the car
   * @param model represents the model of the car
   * @param version represents the version of the car
   * @return true if a car with this make,model and version is present
   */
  public Boolean isRegisteredMakeModelVersion(String make, String model, String version) {
    return carRepository
        .findByMakeAndModelAndVersionAndIsDeletedFalse(make, model, version)
        .isPresent();
  }

  /**
   * Retrieves the authenticated user
   *
   * @return the authenticated {@link User}
   */
  public User getLoggedInUser() {
    return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
  }

  /**
   * Patches the car object
   *
   * <p>Updates the field in the car object provided by the patch
   *
   * @param car {@link Car} takes in the car object that needs to be patched
   * @param patch {@link PatchCar} takes in the patch containing new values for the car
   */
  public void updateCarFields(Car car, PatchCar patch) {
    if (patch.make() != null) {
      car.setMake(patch.make());
    }
    if (patch.model() != null) {
      car.setModel(patch.model());
    }
    if (patch.version() != null) {
      car.setVersion(patch.version());
    }
    if (patch.numberOfDoors() != null) {
      car.setNumberOfDoors(patch.numberOfDoors());
    }
    if (patch.co2Emission() != null) {
      car.setCo2Emission(patch.co2Emission());
    }
    if (patch.grossPrice() != null) {
      car.setGrossPrice(patch.grossPrice());
    }
    if (patch.nettPrice() != null) {
      car.setNettPrice(patch.nettPrice());
    }
  }
}
