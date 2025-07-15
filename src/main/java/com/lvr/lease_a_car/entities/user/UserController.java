package com.lvr.lease_a_car.entities.user;

import com.lvr.lease_a_car.entities.user.dto.*;
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

@RestController
@RequestMapping(Endpoints.USERS)
@RequiredArgsConstructor
public class UserController {
  private final UserService userService;

  @Operation(
      summary = "create user",
      description = "creates a new user and saves it in the database")
  @ApiResponses({
    @ApiResponse(responseCode = "201", description = "user created and saved in the database"),
    @ApiResponse(
        responseCode = "400",
        description = "invalid request body, user already registered")
  })
  @PostMapping("/register")
  public ResponseEntity<GetUserWithJwtToken> registerUser(@RequestBody @Valid PostUser body) {
    GetUserWithJwtToken user = userService.registerUser(body);

    URI location =
        UriComponentsBuilder.newInstance().path("/users/{id}").buildAndExpand(user.id()).toUri();
    return ResponseEntity.created(location).body(user);
  }

  @Operation(summary = "update user", description = "updates user and save it in the database")
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "user updated"),
    @ApiResponse(responseCode = "400", description = "invalid request body"),
    @ApiResponse(responseCode = "404", description = "user with given ID not found")
  })
  @PatchMapping("/{id}")
  public ResponseEntity<GetUser> updateUser(
      @PathVariable Long id, @RequestBody @Valid PatchUser patch) {
    GetUser customer = userService.updateUser(id, patch);

    return ResponseEntity.ok(customer);
  }

  @Operation(summary = "delete user", description = "deletes a user from the database")
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "user deleted"),
    @ApiResponse(responseCode = "404", description = "user with given ID not found")
  })
  @PreAuthorize("hasRole('ADMIN')")
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
    userService.deleteUser(id);
    return ResponseEntity.ok().build();
  }

  // login
  @PostMapping("/login")
  public ResponseEntity<GetUserWithJwtToken> login(@RequestBody LoginUser requestBody) {
    GetUserWithJwtToken user = userService.login(requestBody);
    return ResponseEntity.ok(user);
  }
}
