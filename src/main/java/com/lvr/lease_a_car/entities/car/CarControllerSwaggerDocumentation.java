package com.lvr.lease_a_car.entities.car;

import com.lvr.lease_a_car.entities.car.dto.GetCar;
import com.lvr.lease_a_car.entities.car.dto.GetLeaseRate;
import com.lvr.lease_a_car.entities.car.dto.PatchCar;
import com.lvr.lease_a_car.entities.car.dto.PostCar;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "Cars controller", description = "Operations related to car management")
public interface CarControllerSwaggerDocumentation {

  @Operation(summary = "Create car", description = "Accessible to the following roles: ADMIN")
  @ApiResponses({
    @ApiResponse(responseCode = "201", description = "Car created"),
    @ApiResponse(
        responseCode = "400",
        description =
            "car with the same make, model and version already exists, invalid request body provided")
  })
  public ResponseEntity<GetCar> postCar(PostCar postCar);

  @Operation(
      summary = "get lease rate of car",
      description =
          "returns the lease rate of a car given the mileage of the car, the interest rate and the duration of the lease contract. Accessible to the following roles: EMPLOYEE, BROKER and ADMIN")
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "returns calculated lease-rate"),
    @ApiResponse(responseCode = "404", description = "Car with given ID not found"),
    @ApiResponse(responseCode = "400", description = "missing or invalid query parameters")
  })
  @Parameter(name = "id", description = "car ID", required = true)
  @Parameter(
      name = "duration",
      description = "duration of the contract in months",
      allowEmptyValue = false)
  @Parameter(name = "mileage", description = "mileage of car", allowEmptyValue = false)
  @Parameter(name = "interestRate", description = "interest rate", allowEmptyValue = false)
  public ResponseEntity<GetLeaseRate> getLeaseRate(
      Long id, String duration, String interestRate, String mileage);

  @Operation(
      summary = "Retrieve Car by ID",
      description =
          """
                                returns car with the provided ID.
                                If user is an admin, soft-deleted cars are available.
                                Accessible to the following roles: EMPLOYEE, BROKER and ADMIN""")
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "Car with given ID found"),
    @ApiResponse(responseCode = "404", description = "Car with specified ID not found")
  })
  public ResponseEntity<GetCar> getCarById(Long id);

  @Operation(
      summary = "Get a list of all Cars",
      description =
          "returns a list of all Cars. If user is Admin list contains soft-deleted cars as well. Accessible to the following roles: EMPLOYEE, BROKER and ADMIN")
  @ApiResponse(responseCode = "200", description = "List of Cars successfully retrieved")
  public ResponseEntity<List<GetCar>> getAllCars();

  @Operation(
      summary = "Update car",
      description =
          "updates a car and stores it in the database. Accessible to the following roles: ADMIN ")
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "Car successfully updated"),
    @ApiResponse(responseCode = "400", description = "Invalid request body or parameters"),
    @ApiResponse(responseCode = "404", description = "Car with given ID not found")
  })
  public ResponseEntity<GetCar> patchCar(Long id, PatchCar patchCar);

  @Operation(
      summary = "delete car",
      description = "soft-deletes a car. Accessible to the following roles: ADMIN")
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "car deleted"),
    @ApiResponse(responseCode = "404", description = "car with given ID does not exist")
  })
  public ResponseEntity<Void> deleteCar(Long id);

  @Operation(
      summary = "upload cars from a csv file",
      description =
          "reads cars from csv file and converts them into the corresponding entities and saves them in the database. Accessible to the following roles: ADMIN")
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "cars uploaded"),
    @ApiResponse(responseCode = "400", description = "invalid csv file")
  })
  public ResponseEntity<Void> uploadCars(MultipartFile file);
}
