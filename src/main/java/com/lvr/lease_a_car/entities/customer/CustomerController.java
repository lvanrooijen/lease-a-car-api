package com.lvr.lease_a_car.entities.customer;

import com.lvr.lease_a_car.entities.customer.dto.GetCustomer;
import com.lvr.lease_a_car.entities.customer.dto.PatchCustomer;
import com.lvr.lease_a_car.entities.customer.dto.PostCustomer;
import com.lvr.lease_a_car.utils.constants.routes.Endpoints;
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
public class CustomerController implements CustomerControllerSwaggerDocumentation {
  private final CustomerService customerService;

  @Override
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

  @Override
  @PreAuthorize("hasRole('BROKER') or hasRole('ADMIN')")
  @PatchMapping("/{id}")
  public ResponseEntity<GetCustomer> updateCustomer(
      @PathVariable Long id, @RequestBody @Valid PatchCustomer patch) {
    GetCustomer customer = customerService.patchCustomer(id, patch);
    return ResponseEntity.ok(customer);
  }

  @Override
  @PreAuthorize("hasRole('BROKER') or hasRole('ADMIN')")
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
    customerService.deleteCustomer(id);
    return ResponseEntity.ok().build();
  }
}
