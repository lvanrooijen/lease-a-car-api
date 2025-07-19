package com.lvr.lease_a_car.entities.user;

import com.lvr.lease_a_car.entities.user.dto.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;

public interface UserControllerSwaggerDocumentation {

  @Operation(
      summary = "create user",
      description = "creates a new user and saves it in the database")
  @ApiResponses({
    @ApiResponse(responseCode = "201", description = "user created and saved in the database"),
    @ApiResponse(
        responseCode = "400",
        description = "invalid request body, user already registered")
  })
  public ResponseEntity<GetUserWithJwtToken> registerUser(PostUser body);

  @Operation(summary = "update user", description = "updates user and save it in the database")
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "user updated"),
    @ApiResponse(responseCode = "400", description = "invalid request body"),
    @ApiResponse(responseCode = "404", description = "user with given ID not found")
  })
  public ResponseEntity<GetUser> updateUser(Long id, PatchUser patch);

  @Operation(summary = "delete user", description = "deletes a user from the database")
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "user deleted"),
    @ApiResponse(responseCode = "404", description = "user with given ID not found")
  })
  public ResponseEntity<Void> deleteUser(Long id);

  @Operation(summary = "login user", description = "login a user with a jwt token")
  @ApiResponses({@ApiResponse(responseCode = "200", description = "user logged in")})
  public ResponseEntity<GetUserWithJwtToken> login(LoginUser requestBody);
}
