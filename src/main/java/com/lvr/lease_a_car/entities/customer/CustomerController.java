package com.lvr.lease_a_car.entities.customer;

import com.lvr.lease_a_car.entities.customer.dto.GetCustomer;
import com.lvr.lease_a_car.entities.customer.dto.PatchCustomer;
import com.lvr.lease_a_car.entities.customer.dto.PostCustomer;
import com.lvr.lease_a_car.utils.constants.routes.Endpoints;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

/** handles http requests related to customers */
@RestController
@RequestMapping(Endpoints.CUSTOMERS)
@RequiredArgsConstructor
public class CustomerController {
  private final CustomerService customerService;

  @Operation(
      summary = "create a customer",
      description = "Accessible to the following roles: EMPLOYEE, BROKER and ADMIN")
  @ApiResponses({
    @ApiResponse(responseCode = "201", description = "customer created"),
    @ApiResponse(responseCode = "400", description = "invalid request body")
  })
  @PreAuthorize("hasRole('BROKER') or hasRole('ADMIN') or hasRole('EMPLOYEE')")
  @PostMapping("/register")
  public ResponseEntity<GetCustomer> createCustomer(@Valid @RequestBody PostCustomer body) {
    GetCustomer customer = customerService.createCustomer(body);
    URI location =
        UriComponentsBuilder.newInstance()
            .path("/customers/{id}")
            .buildAndExpand(customer.id())
            .toUri();
    return ResponseEntity.created(location).body(customer);
  }

  @Operation(
      summary = "update a customer",
      description = "Accessible to the following roles: BROKER and ADMIN")
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "customer updated"),
    @ApiResponse(responseCode = "404", description = "customer with given ID not found"),
    @ApiResponse(responseCode = "400", description = "invalid request body")
  })
  @PreAuthorize("hasRole('BROKER') or hasRole('ADMIN')")
  @PatchMapping("/{id}")
  public ResponseEntity<GetCustomer> updateCustomer(
      @PathVariable Long id, @RequestBody @Valid PatchCustomer patch) {
    GetCustomer customer = customerService.patchCustomer(id, patch);
    return ResponseEntity.ok(customer);
  }

  @Operation(
      summary = "delete a customer",
      description = "Deletes a customer. Accessible to the following roles: BROKER and ADMIN")
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "customer deleted"),
    @ApiResponse(responseCode = "404", description = "customer with given ID not found")
  })
  @PreAuthorize("hasRole('BROKER') or hasRole('ADMIN')")
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
    customerService.deleteCustomer(id);
    return ResponseEntity.ok().build();
  }
}
