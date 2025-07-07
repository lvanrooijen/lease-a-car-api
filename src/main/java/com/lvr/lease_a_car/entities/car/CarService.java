package com.lvr.lease_a_car.entities.car;

import com.lvr.lease_a_car.entities.car.dto.GetCar;
import com.lvr.lease_a_car.entities.car.dto.GetLeaseRate;
import com.lvr.lease_a_car.entities.car.dto.PatchCar;
import com.lvr.lease_a_car.entities.car.dto.PostCar;
import com.lvr.lease_a_car.exception.ExistingCarException;
import jakarta.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CarService {
  private final CarRepository carRepository;

  public GetCar getCarById(Long id) {
    // if is lease company show deleted cars
    // TODO
    // if broker show non-deleted cars
    Car car =
        carRepository
            .findByIdAndIsDeletedFalse(id)
            .orElseThrow(
                () -> new EntityNotFoundException("Car by id " + id + " can not be found"));

    return GetCar.to(car);
  }

  public List<GetCar> getAllCars() {
    // if admin just return find all cars
    // todo
    // if broker return non-deleted cars
    List<Car> cars = carRepository.findAllByIsDeletedFalse();
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
    if (car.isDeleted()) {
      // if admin go head else throw exc
      // throw new ForbiddenException("Admin only action");
    }

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
    // TODO nog even dubbel checke welke mogelijke verkeerde waardes kunnen worden meegegeven bij
    // duration en interest rate en wat dan? en wat als null
    Car car =
        carRepository
            .findById(carId)
            .orElseThrow(
                () -> new EntityNotFoundException("Car with id " + carId + " can not be found"));

    BigDecimal mileageBD = BigDecimal.valueOf(mileage);
    BigDecimal nettPrice = BigDecimal.valueOf(car.getNettPrice());
    BigDecimal interestRateBD = new BigDecimal(interestRate);

    // ((( mileage / 12 ) * duration ) / Nett price) +
    // ((( Interest rate / 100 ) * Nett price) / 12 )
    // TODO testen of ik geen fouten heb gemaakt
    BigDecimal leaseRate =
        mileageBD
            .divide(BigDecimal.valueOf(12), RoundingMode.CEILING)
            .multiply(BigDecimal.valueOf(duration))
            .divide(nettPrice, RoundingMode.CEILING)
            .add(
                interestRateBD
                    .divide(BigDecimal.valueOf(100), RoundingMode.CEILING)
                    .multiply(nettPrice)
                    .divide(BigDecimal.valueOf(12), RoundingMode.CEILING));
    return GetLeaseRate.to(leaseRate);
  }

  /**
   * Verifies if a car with this make, model and version is already present in the database
   *
   * @param make
   * @param model
   * @param version
   * @return Boolean
   */
  public Boolean isRegistered(String make, String model, String version) {
    return carRepository
        .findByMakeAndModelAndVersionAndIsDeletedFalse(make, model, version)
        .isPresent();
  }
}
