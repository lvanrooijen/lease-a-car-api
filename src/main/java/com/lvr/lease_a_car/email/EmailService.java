package com.lvr.lease_a_car.email;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Slf4j
@RequiredArgsConstructor
@Service
public class EmailService {
  private final JavaMailSender mailSender;
  private final TemplateEngine templateEngine;

  public void sendMail(String to, String subject, String text) {
    SimpleMailMessage message = new SimpleMailMessage();
    message.setTo(to);
    message.setSubject(subject);
    message.setText(text);
    mailSender.send(message);
  }

  public void sendUserRegisterationEmail(String to, String username) throws MessagingException {
    Context context = new Context();
    context.setVariable("username", username);

    String body = templateEngine.process("UserRegistration.html", context);

    MimeMessage message = mailSender.createMimeMessage();
    MimeMessageHelper helper = new MimeMessageHelper(message, true);
    helper.setTo(to);
    helper.setSubject(String.format("Welcome %s!", username));
    helper.setText(body, true);
    mailSender.send(message);
  }
}
