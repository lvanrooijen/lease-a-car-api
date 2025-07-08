package com.lvr.lease_a_car.utils.configuration;

import java.io.PrintStream;
import org.springframework.boot.Banner;
import org.springframework.core.env.Environment;

public class CustomBanner implements Banner {
  private static final String SEA_GREEN = "\u001B[38;2;0;145;110m";
  private static final String BLUE = "\u001B[38;2;0;144;193m";

  private static final String BANNER =
      BLUE
          + "██      ███████  █████  ███████ ███████        █████         ██████  █████  ██████ \n"
          + "██      ██      ██   ██ ██      ██            ██   ██       ██      ██   ██ ██   ██ \n"
          + "██      █████   ███████ ███████ █████   █████ ███████ █████ ██      ███████ ██████ \n"
          + "██      ██      ██   ██      ██ ██            ██   ██       ██      ██   ██ ██   ██ \n"
          + "███████ ███████ ██   ██ ███████ ███████       ██   ██        ██████ ██   ██ ██   ██ \n"
          + SEA_GREEN;

  @Override
  public void printBanner(Environment environment, Class<?> sourceClass, PrintStream out) {
    out.println(BANNER);
  }
}
