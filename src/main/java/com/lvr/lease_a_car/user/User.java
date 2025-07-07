package com.lvr.lease_a_car.user;

import jakarta.persistence.*;
import java.util.Collection;
import java.util.List;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User implements UserDetails {

  @Builder
  public User(String email, String password, Role role, String firstName, String lastName) {
    this.email = email;
    this.password = password;
    this.role = role;
    this.firstName = firstName;
    this.lastName = lastName;
  }

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Long id;

  @Column(name = "email", nullable = false, unique = true)
  String email;

  @Column(name = "password", nullable = false)
  String password;

  @Column(name = "role", nullable = false)
  @Enumerated(EnumType.STRING)
  Role role;

  @Column(name = "first_name", nullable = false)
  private String firstName;

  @Column(name = "last_name", nullable = false)
  private String lastName;

  public Collection<? extends GrantedAuthority> getAuthorities() {
    return List.of(new SimpleGrantedAuthority(role.toString()));
  }

  @Override
  public String getUsername() {
    return this.email;
  }

  public String getRole() {
    return this.role.toString();
  }

  public Boolean isAdmin() {
    return role.equals(Role.ADMIN);
  }
}
