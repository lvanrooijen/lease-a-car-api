package com.lvr.lease_a_car.events.userregistration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class UserRegistrationEventListener implements ApplicationListener<UserRegistrationEvent> {

  @Override
  public void onApplicationEvent(UserRegistrationEvent event) {
    log.info(
        String.format(
            "[USER REGISTRATION EVENT] [email=%s] [userID=%s]",
            event.getEmail(), event.getUserId()));

    // TODO send welcome email to user
  }
}
