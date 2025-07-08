package com.lvr.lease_a_car.entities.user;

import com.lvr.lease_a_car.entities.user.dto.GetUser;
import com.lvr.lease_a_car.entities.user.dto.PatchUser;
import com.lvr.lease_a_car.entities.user.dto.PostUser;
import com.lvr.lease_a_car.utils.constants.routes.Routes;
import jakarta.validation.Valid;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping(Routes.USERS)
@RequiredArgsConstructor
public class UserController {
  private final UserService userService;

  @PostMapping("/register")
  public ResponseEntity<GetUser> registerUser(@RequestBody @Valid PostUser body) {
    GetUser user = userService.registerUser(body);

    URI location =
        UriComponentsBuilder.newInstance().path("/users/{id}").buildAndExpand(user.id()).toUri();
    return ResponseEntity.created(location).body(user);
  }

  @PatchMapping("/{id}")
  public ResponseEntity<GetUser> updateUser(
      @PathVariable Long id, @RequestBody @Valid PatchUser patch) {
    GetUser customer = userService.updateUser(id, patch);

    return ResponseEntity.ok(customer);
  }

  @PreAuthorize("hasRole('ADMIN')")
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
    userService.deleteUser(id);
    return ResponseEntity.ok().build();
  }
}
