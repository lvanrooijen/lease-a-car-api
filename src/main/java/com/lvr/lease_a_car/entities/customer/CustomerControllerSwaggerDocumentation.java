package com.lvr.lease_a_car.entities.customer;

import com.lvr.lease_a_car.entities.customer.dto.GetCustomer;
import com.lvr.lease_a_car.entities.customer.dto.PatchCustomer;
import com.lvr.lease_a_car.entities.customer.dto.PostCustomer;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

public interface CustomerControllerSwaggerDocumentation {

  @Operation(
      summary = "create a customer",
      description = "Accessible to the following roles: EMPLOYEE, BROKER and ADMIN")
  @ApiResponses({
    @ApiResponse(responseCode = "201", description = "customer created"),
    @ApiResponse(responseCode = "400", description = "invalid request body")
  })
  public ResponseEntity<GetCustomer> createCustomer(PostCustomer body);

  @Operation(
      summary = "update a customer",
      description = "Accessible to the following roles: BROKER and ADMIN")
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "customer updated"),
    @ApiResponse(responseCode = "404", description = "customer with given ID not found"),
    @ApiResponse(responseCode = "400", description = "invalid request body")
  })
  public ResponseEntity<GetCustomer> updateCustomer(Long id, PatchCustomer patch);

  @Operation(
      summary = "delete a customer",
      description = "Accessible to the following roles: BROKER and ADMIN")
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "customer deleted"),
    @ApiResponse(responseCode = "404", description = "customer with given ID not found")
  })
  public ResponseEntity<Void> deleteCustomer(@PathVariable Long id);
}
