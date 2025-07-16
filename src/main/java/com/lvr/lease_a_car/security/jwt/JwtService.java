package com.lvr.lease_a_car.security.jwt;

import static com.lvr.lease_a_car.utils.constants.JwtConstants.JWT_EXPIRATION_MS;
import static com.lvr.lease_a_car.utils.constants.JwtConstants.ROLES_CLAIM_NAME;

import com.lvr.lease_a_car.entities.user.User;
import com.lvr.lease_a_car.security.jwt.dto.JwtTokenDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;
import javax.crypto.SecretKey;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

/** Jwt service is responsible for generating and validating the token */
@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class JwtService {
  private final SecretKey jwtSecretKey;

  /**
   * generates a jwt token for the user
   *
   * @param user who the token is generated for
   * @return jwt token
   */
  public String generateTokenForUser(User user) {
    long currentTimeMillis = System.currentTimeMillis();

    return Jwts.builder()
        .claim(ROLES_CLAIM_NAME, convertAuthoritiesToRoles(user))
        .subject(user.getEmail())
        .issuedAt(new Date(currentTimeMillis))
        .expiration(new Date(currentTimeMillis + JWT_EXPIRATION_MS))
        .signWith(jwtSecretKey)
        .compact();
  }

  /**
   * Converts users granted authorities to a list of roles in string format
   *
   * @param user user
   * @return list of user roles as a string format
   */
  public List<String> convertAuthoritiesToRoles(User user) {
    return user.getAuthorities().stream()
        .map(GrantedAuthority::getAuthority)
        .collect(Collectors.toList());
  }

  public Optional<JwtTokenDetails> readToken(String token) {
    try {
      Claims claims =
          Jwts.parser().verifyWith(jwtSecretKey).build().parseSignedClaims(token).getPayload();
      return Optional.of(
          new JwtTokenDetails(
              claims.getSubject(),
              getRolesFromClaims(claims),
              claims.getIssuedAt(),
              claims.getExpiration()));
    } catch (RuntimeException ex) {

      log.warn("[" + ex.getClass().getName() + "]" + " " + ex.getMessage());

      return Optional.empty();
    }
  }

  private String[] getRolesFromClaims(Claims claims) {
    Object rolesObject = claims.get(ROLES_CLAIM_NAME);

    if (rolesObject == null) {
      throw new IllegalArgumentException(ROLES_CLAIM_NAME + " claim not found");
    }

    if (!(rolesObject instanceof Iterable<?> rawRoles)) {
      throw new IllegalArgumentException("claims " + ROLES_CLAIM_NAME + " value is invalid");
    }

    List<String> parsedRoles = new ArrayList<>();

    for (Object o : rawRoles) {
      if (o instanceof String parsedRole) {
        parsedRoles.add(parsedRole);
      } else {
        log.warn(String.format("role is not a valid type: %s", o.getClass().getName()));
      }
    }

    return parsedRoles.toArray(new String[0]);
  }
}
