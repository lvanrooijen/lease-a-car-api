package com.lvr.lease_a_car.security.jwt.dto;

import java.util.Date;

public record JwtTokenDetails(String email, String[] roles, Date issuedAt, Date expiresAt) {

  public boolean isExpired() {
    return expiresAt.before(new Date());
  }
}
