package com.lvr.lease_a_car.user;

import com.lvr.lease_a_car.user.dto.GetCustomer;
import com.lvr.lease_a_car.user.dto.PatchCustomer;
import com.lvr.lease_a_car.user.dto.PostCustomer;
import com.lvr.lease_a_car.utils.constants.routes.Routes;
import jakarta.validation.Valid;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping(Routes.CUSTOMERS)
@RequiredArgsConstructor
public class CustomerController {
  private final UserService userService;

  @PostMapping("/register")
  public ResponseEntity<GetCustomer> registerCustomer(@RequestBody @Valid PostCustomer body) {
    GetCustomer user = userService.registerCustomer(body);

    URI location =
        UriComponentsBuilder.newInstance()
            .path("/customers/{id}")
            .buildAndExpand(user.id())
            .toUri();
    return ResponseEntity.created(location).body(user);
  }

  @PatchMapping("/{id}")
  public ResponseEntity<GetCustomer> updateCustomer(
      @PathVariable Long id, @RequestBody @Valid PatchCustomer patch) {
    GetCustomer customer = userService.updateCustomer(id, patch);

    return ResponseEntity.ok(customer);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
    userService.deleteCustomer(id);
    return ResponseEntity.ok().build();
  }
}
