package com.lvr.lease_a_car.utils.constants.routes;

/** Contains constants holding endpoints for security configuration */
public class SecurityRoutes {
  private static final String BASE = "/api/v1";
  private static final String REGISTER_USERS = BASE + "/users/**";
  private static final String SWAGGER_UI = "/swagger-ui/**";
  private static final String SWAGGER_DOCS = "/v3/api-docs*/**";

  /**
   * Method that returns endpoints that don't require authentication
   *
   * @return String[] containing endpoints
   */
  public static String[] getNonAuthenticatedEndpoints() {
    return new String[] {REGISTER_USERS, SWAGGER_UI, SWAGGER_DOCS};
  }
}
