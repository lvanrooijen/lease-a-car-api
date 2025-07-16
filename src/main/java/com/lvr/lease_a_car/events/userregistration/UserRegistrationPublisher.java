package com.lvr.lease_a_car.events.userregistration;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserRegistrationPublisher {
  private final ApplicationEventPublisher publisher;

  public void publishUserRegistrationEvent(final String email, final Long userId) {
    UserRegistrationEvent userRegistrationEvent = new UserRegistrationEvent(this, email, userId);

    publisher.publishEvent(userRegistrationEvent);
  }
}
