package com.lvr.lease_a_car.utils.constants.routes;

public class SecurityRoutes {
  private static final String BASE = "/api/v1";
  private static final String REGISTER_USERS = BASE + "/users/**";
  private static final String CUSTOMERS = BASE + "/customers";

  // TODO delete me later! temp
  private static final String TEST = BASE + "/test";

  public static String[] getOpenPostPaths() {
    return new String[] {REGISTER_USERS, TEST};
  }
}
