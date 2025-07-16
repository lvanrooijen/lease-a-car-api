package com.lvr.lease_a_car.entities.car;

import com.lvr.lease_a_car.entities.car.dto.*;
import com.lvr.lease_a_car.entities.user.User;
import com.lvr.lease_a_car.exception.car.*;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.HeaderColumnNameMappingStrategy;
import jakarta.persistence.EntityNotFoundException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/** Handles the business logic related to cars */
@Service
@RequiredArgsConstructor
public class CarService {
  private final CarRepository carRepository;
  private final CarMapper carMapper;
  private final LeaseRateService leaseRateService;

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
    Car car = carMapper.toCarEntity(postCar);

    carRepository.save(car);
    return carMapper.toGetCarDto(car);
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
      car = carRepository.findById(id).orElse(null);
    } else {
      car = carRepository.findByIdAndIsDeletedFalse(id).orElse(null);
    }
    if (car == null) {
      throw new CarNotFoundException(String.format("Car with ID %d not found", id));
    }
    return carMapper.toGetCarDto(car);
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
    return cars.stream().map(carMapper::toGetCarDto).toList();
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
                () ->
                    new CarNotFoundException(
                        String.format(
                            "Failed to calculate lease rate, car with ID %d not found", carId)));

    BigDecimal leaseRate =
        leaseRateService.calculateLeaseRate(
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
                    new CarModificationException(
                        String.format(
                            "Failed to update car, Car with id %d can not be found", id)));

    carMapper.updateCarFields(car, patch);
    carRepository.save(car);
    return carMapper.toGetCarDto(car);
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
                    new CarModificationException(
                        String.format(
                            "Failed to delete car, a car with id %d can not be found.", id)));
    car.setDeleted(true);
    carRepository.save(car);
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
   * uploads cars from a csv file to the database
   *
   * @param file csv file containing car data
   */
  public void uploadCars(MultipartFile file) {
    Set<Car> cars;
    try {
      cars = parseCsv(file);
    } catch (IOException e) {
      throw new CarCreationException("Failed to convert csv file");
    }
    carRepository.saveAll(cars);
  }

  /**
   * reads the csv file and maps the cars to a set
   *
   * @param file csv file containing car data
   * @return {@link Car} set of cars extracted from the csv file
   * @throws IOException when an error occurs reading the file
   */
  private Set<Car> parseCsv(MultipartFile file) throws IOException {
    try (Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
      HeaderColumnNameMappingStrategy<CarCsvRepresentation> strategy =
          new HeaderColumnNameMappingStrategy<>();
      strategy.setType(CarCsvRepresentation.class);
      CsvToBean<CarCsvRepresentation> csvToBean =
          new CsvToBeanBuilder<CarCsvRepresentation>(reader)
              .withSeparator(';')
              .withMappingStrategy(strategy)
              .withIgnoreEmptyLine(true)
              .withIgnoreLeadingWhiteSpace(true)
              .build();

      return csvToBean.parse().stream()
          .map(
              csvLine ->
                  Car.builder()
                      .make(csvLine.getMake())
                      .model(csvLine.getModel())
                      .version(csvLine.getVersion())
                      .numberOfDoors(csvLine.getNumberOfDoors())
                      .grossPrice(convertToDouble("grossPrice", csvLine.getGrossPrice()))
                      .nettPrice(convertToDouble("nettPrice", csvLine.getNettPrice()))
                      .build())
          .collect(Collectors.toSet());
    }
  }

  /**
   * method that converts a string into a double value
   *
   * @param type type of column that is being converted
   * @param number number that needs to be converted to a double
   * @return converted number as a double value
   */
  public double convertToDouble(String type, String number) {
    NumberFormat formatter = NumberFormat.getInstance();
    double value;
    try {
      value = formatter.parse(number).doubleValue();
    } catch (ParseException e) {
      throw new CarCreationException("invalid value for " + type + " failed to convert: " + number);
    }
    return value;
  }
}
