package com.lvr.lease_a_car;

import com.lvr.lease_a_car.utils.configuration.CustomBanner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LeaseACarApplication {

  public static void main(String[] args) {
    SpringApplication application = new SpringApplication(LeaseACarApplication.class);
    application.setBanner(new CustomBanner());
    application.run(args);
  }
}
