package com.lvr.lease_a_car.utils.constants.routes;

/** Contains constants holding endpoints for security configuration */
public class SecurityRoutes {
  private static final String BASE = "/api/v1";
  private static final String REGISTER_USERS = BASE + "/users/**";
  private static final String CUSTOMERS = BASE + "/customers";

  /**
   * Method that returns endpoints that don't require authentication
   *
   * @return String[] containing endpoints
   */
  public static String[] getOpenPostPaths() {
    return new String[] {REGISTER_USERS};
  }
}
