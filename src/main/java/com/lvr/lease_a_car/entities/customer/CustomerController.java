package com.lvr.lease_a_car.entities.customer;

import com.lvr.lease_a_car.entities.customer.dto.GetCustomer;
import com.lvr.lease_a_car.entities.customer.dto.PatchCustomer;
import com.lvr.lease_a_car.entities.customer.dto.PostCustomer;
import com.lvr.lease_a_car.utils.constants.routes.Routes;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping(Routes.CUSTOMERS)
@RequiredArgsConstructor
public class CustomerController {
  private final CustomerService customerService;

  @PostMapping
  public ResponseEntity<GetCustomer> createCustomer(@RequestBody PostCustomer body) {
    GetCustomer customer = customerService.createCustomer(body);
    URI location =
        UriComponentsBuilder.newInstance()
            .path("/customers/{id}")
            .buildAndExpand(customer.id())
            .toUri();
    return ResponseEntity.created(location).body(customer);
  }

  @PatchMapping("/{id}")
  public ResponseEntity<GetCustomer> updateCustomer(
      @PathVariable Long id, @RequestBody PatchCustomer patch) {
    GetCustomer customer = customerService.patchCustomer(id, patch);
    return ResponseEntity.ok(customer);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
    customerService.deleteCustomer(id);
    return ResponseEntity.ok().build();
  }
}
