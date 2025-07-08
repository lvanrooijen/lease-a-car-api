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

@Service
@RequiredArgsConstructor
public class CarService {
  private final CarRepository carRepository;

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

  public GetCar createCar(PostCar postCar) {
    if (isRegistered(postCar.make(), postCar.model(), postCar.version())) {
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

  public GetLeaseRate getLeaseRate(
      Long carId, Double duration, Double interestRate, Double mileage) {
    Car car =
        carRepository
            .findById(carId)
            .orElseThrow(
                () -> new EntityNotFoundException("Car with id " + carId + " can not be found"));

    BigDecimal leaseRate =
        calculateLeaseRate(
            BigDecimal.valueOf(mileage),
            BigDecimal.valueOf(duration),
            BigDecimal.valueOf(interestRate),
            BigDecimal.valueOf(car.getNettPrice()));

    return GetLeaseRate.to(leaseRate, car);
  }

  /**
   * Calculates the lease rate of a car
   *
   * <p>Formula: ((( mileage / 12 ) * duration ) / Nett price) + ((( Interest rate / 100 ) * Nett
   * price) / 12 )
   *
   * @param mileage
   * @param duration
   * @param interestRate
   * @param nettPrice
   * @return lease rate
   */
  public BigDecimal calculateLeaseRate(
      BigDecimal mileage, BigDecimal duration, BigDecimal interestRate, BigDecimal nettPrice) {
    // ((( mileage / 12 ) * duration ) / Nett price)
    BigDecimal mileagePerMonth = mileage.divide(BigDecimal.valueOf(12), RoundingMode.FLOOR);
    BigDecimal monthlyMileageDuration = mileagePerMonth.multiply(duration);
    BigDecimal resultPartOne = monthlyMileageDuration.divide(nettPrice, 20, RoundingMode.FLOOR);

    // ((( Interest rate / 100 ) * Nett price) / 12 )
    BigDecimal intRate = interestRate.divide(BigDecimal.valueOf(100), 20, RoundingMode.FLOOR);
    BigDecimal intRateNetPrice = intRate.multiply(nettPrice);
    BigDecimal resultPartTwo =
        intRateNetPrice.divide(BigDecimal.valueOf(12), 20, RoundingMode.FLOOR);

    // add part 1 and part 2
    BigDecimal result = resultPartOne.add(resultPartTwo);

    return result.setScale(2, RoundingMode.FLOOR);
  }

  /**
   * Checks if a car with this make, model and version is already present in the database. Soft
   * deleted Cars are not taken into consideration
   *
   * @param make
   * @param model
   * @param version
   * @return true if a car with this make,model and version is present
   */
  public Boolean isRegistered(String make, String model, String version) {
    return carRepository
        .findByMakeAndModelAndVersionAndIsDeletedFalse(make, model, version)
        .isPresent();
  }

  public User getLoggedInUser() {
    return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
  }

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
