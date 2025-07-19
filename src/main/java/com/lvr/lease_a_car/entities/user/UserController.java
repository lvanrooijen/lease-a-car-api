package com.lvr.lease_a_car.entities.user;

import com.lvr.lease_a_car.entities.user.dto.*;
import com.lvr.lease_a_car.utils.constants.routes.Endpoints;
import jakarta.validation.Valid;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping(Endpoints.USERS)
@RequiredArgsConstructor
public class UserController implements UserControllerSwaggerDocumentation {
  private final UserService userService;

  @Override
  @PostMapping("/register")
  public ResponseEntity<GetUserWithJwtToken> registerUser(@RequestBody @Valid PostUser body) {
    GetUserWithJwtToken user = userService.registerUser(body);

    URI location =
        UriComponentsBuilder.newInstance().path("/users/{id}").buildAndExpand(user.id()).toUri();
    return ResponseEntity.created(location).body(user);
  }

  @Override
  @PatchMapping("/{id}")
  public ResponseEntity<GetUser> updateUser(
      @PathVariable Long id, @RequestBody @Valid PatchUser patch) {
    GetUser customer = userService.updateUser(id, patch);

    return ResponseEntity.ok(customer);
  }

  @Override
  @PreAuthorize("hasRole('ADMIN')")
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
    userService.deleteUser(id);
    return ResponseEntity.ok().build();
  }

  @Override
  @PostMapping("/login")
  public ResponseEntity<GetUserWithJwtToken> login(@RequestBody LoginUser requestBody) {
    GetUserWithJwtToken user = userService.login(requestBody);
    return ResponseEntity.ok(user);
  }
}
