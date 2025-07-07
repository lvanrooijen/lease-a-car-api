package com.lvr.lease_a_car.entities.user;

public enum Role {
  BROKER("ROLE_BROKER"),
  EMPLOYEE("ROLE_EMPLOYEE"),
  ADMIN("ROLE_ADMIN");

  private final String role;

  Role(String role) {
    this.role = role;
  }

  @Override
  public String toString() {
    return role;
  }
}
