package com.lvr.lease_a_car.email;

import org.springframework.context.annotation.Bean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;

@Component
public class EmailConfiguration {
  @Bean
  public JavaMailSender getMailSender() {
    JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
    mailSender.setHost("localhost");
    mailSender.setPort(1025);
    return mailSender;
  }

  @Bean
  public TemplateEngine getTemplateEngine() {
    return new TemplateEngine();
  }
}
