package com.lvr.lease_a_car.events.userregistration;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class UserRegistrationEvent extends ApplicationEvent {
  private String email;
  private Long userId;
  private String fullName;

  public UserRegistrationEvent(Object source, String email, Long userId, String fullName) {
    super(source);
    this.email = email;
    this.userId = userId;
    this.fullName = fullName;
  }
}
