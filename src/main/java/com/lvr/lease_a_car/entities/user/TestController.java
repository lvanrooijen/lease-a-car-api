package com.lvr.lease_a_car.entities.user;

import com.lvr.lease_a_car.utils.constants.routes.Routes;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(Routes.BASE_ROUTE)
@RequiredArgsConstructor
public class TestController {
  private final UserRepository userRepository;

  @GetMapping("/test")
  public List<User> test() {
    return userRepository.findAll();
  }
}
