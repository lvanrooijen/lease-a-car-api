package com.lvr.lease_a_car.events.userregistration;

import com.lvr.lease_a_car.email.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class UserRegistrationEventListener implements ApplicationListener<UserRegistrationEvent> {
  private final EmailService emailService;

  @Override
  public void onApplicationEvent(UserRegistrationEvent event) {
    log.info(
        String.format(
            "[USER REGISTRATION EVENT] [email=%s] [userID=%s]",
            event.getEmail(), event.getUserId()));
    /*
       try {
         emailService.sendUserRegisterationEmail(event.getEmail(), event.getFullName());
         System.out.println("email sent");
       } catch (MessagingException e) {
         log.warn(String.format("[MESSAGING EXCEPTION] detail: %s", e.getMessage()));
       }
       // TODO set up maildev
    */
  }
}
