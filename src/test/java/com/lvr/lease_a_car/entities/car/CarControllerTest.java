package com.lvr.lease_a_car.entities.car;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.lvr.lease_a_car.entities.car.dto.GetLeaseRate;
import com.lvr.lease_a_car.utils.constants.routes.LeaseRateConstants;
import jakarta.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.util.stream.Stream;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

/** Test cases for the car controller class */
@WebMvcTest(
    controllers = CarController.class,
    excludeAutoConfiguration = SecurityAutoConfiguration.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class CarControllerTest {
  @Autowired MockMvc mockMvc;

  @MockitoBean private CarService carService;
  @MockitoBean private CarRepository carRepository;

  @Nested
  class GetLeaseRateTest {
    Long carId = 1L;
    String duration = "12";
    String interestRate = "3.5";
    String mileage = "1000";
    String endpoint;
    GetLeaseRate leaseRateDTO;

    @BeforeEach
    void setUp() {
      endpoint = "/api/v1/cars/" + carId + "/lease-rate";
      leaseRateDTO = (new GetLeaseRate(1L, new BigDecimal("239.76")));
    }

    @Test
    void getLeaseRate_happy_path() throws Exception {
      Mockito.when(
              carService.getLeaseRate(Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any()))
          .thenReturn(leaseRateDTO);

      mockMvc
          .perform(
              get(endpoint)
                  .param("interestRate", interestRate)
                  .param("duration", duration)
                  .param("mileage", mileage)
                  .contentType(MediaType.APPLICATION_JSON))
          .andExpect(status().isOk())
          .andExpect(jsonPath("$.carId").value(1))
          .andExpect(jsonPath("$.leaseRate").value(239.76));
    }

    @ParameterizedTest
    @MethodSource("getInvalidRequestParams")
    void getLeaseRate_given_invalid_query_parameters_should_return_bad_request(
        String interestRate, String duration, String mileage) throws Exception {
      Mockito.when(
              carService.getLeaseRate(Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any()))
          .thenReturn(leaseRateDTO);

      mockMvc
          .perform(
              get(endpoint)
                  .param("interestRate", interestRate)
                  .param("duration", duration)
                  .param("mileage", mileage)
                  .contentType(MediaType.APPLICATION_JSON))
          .andExpect(status().isBadRequest());
    }

    static Stream<Arguments> getInvalidRequestParams() {
      String duration = "12";
      String interestRate = "3.5";
      String mileage = "1000";

      return Stream.of(
          // interestRate
          Arguments.of("a", duration, mileage),
          Arguments.of("-1", duration, mileage),
          Arguments.of("101", duration, mileage),
          Arguments.of("", duration, mileage),
          Arguments.of(" ", duration, mileage),
          Arguments.of(null, duration, mileage),
          // duration
          Arguments.of(interestRate, "-1", mileage),
          Arguments.of(
              interestRate, (String.valueOf(LeaseRateConstants.MIN_DURATION - 1)), mileage),
          Arguments.of(
              interestRate, (String.valueOf(LeaseRateConstants.MAX_DURATION + 1)), mileage),
          Arguments.of(duration, "a", mileage),
          Arguments.of(interestRate, "22.5", mileage),
          Arguments.of(interestRate, "", mileage),
          Arguments.of(interestRate, " ", mileage),
          Arguments.of(interestRate, null, mileage),
          // mileage
          Arguments.of(interestRate, duration, "-1"),
          Arguments.of(interestRate, duration, "a"),
          Arguments.of(interestRate, duration, ""),
          Arguments.of(interestRate, duration, " "),
          Arguments.of(interestRate, duration, null));
    }
  }

  @Nested
  class GetCarByIdTest {
    Car car;
    String endpoint;

    @BeforeEach
    void setUp() {
      endpoint = "/api/v1/cars";
      car =
          Car.builder()
              .make("Citroën")
              .model("2CV")
              .version("6 Club Cabriolet")
              .numberOfDoors(2)
              .nettPrice(12500.00)
              .grossPrice(15125.00)
              .co2Emission(115.0)
              .isDeleted(false)
              .build();
    }

    @Test
    void getCarById_happy_path() throws Exception {
      Mockito.when(carRepository.findById(Mockito.anyLong()))
          .thenReturn(java.util.Optional.ofNullable(car));

      mockMvc
          .perform(get(endpoint + "/1").accept(MediaType.APPLICATION_JSON))
          .andExpect(status().isOk())
          .andExpect(jsonPath("$.carId").value(1));
    }

    @Test
    void getCarById_non_existent_car_id_should_return_not_found() throws Exception {
      Mockito.when(carService.getCarById(1L)).thenThrow(EntityNotFoundException.class);

      mockMvc
          .perform(get(endpoint + "/1").accept(MediaType.APPLICATION_JSON))
          .andExpect(status().isNotFound());
    }
  }
}
