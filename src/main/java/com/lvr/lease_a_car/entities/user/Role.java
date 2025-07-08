package com.lvr.lease_a_car.entities.user;

/**
 * Represent User Roles
 *
 * <p>toString() returns roles with a "ROLE_ prefix"
 */
public enum Role {
  BROKER("ROLE_BROKER"),
  EMPLOYEE("ROLE_EMPLOYEE"),
  ADMIN("ROLE_ADMIN");

  private final String role;

  Role(String role) {
    this.role = role;
  }

  /**
   * Returns a String representation of this role
   *
   * <p>{@link Role} is prefixed with "ROLE_"
   *
   * @return {@link Role}
   */
  @Override
  public String toString() {
    return role;
  }
}
