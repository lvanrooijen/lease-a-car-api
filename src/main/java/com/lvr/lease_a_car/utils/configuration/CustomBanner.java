package com.lvr.lease_a_car.utils.configuration;

import java.io.PrintStream;
import org.springframework.boot.Banner;
import org.springframework.core.env.Environment;

/**
 * Creates a new Banner that gets printed in the terminal and changes the colors of text printed in
 * the terminal
 */
public class CustomBanner implements Banner {
  private static final String SEA_GREEN = "\u001B[38;2;0;145;110m";
  private static final String BLUE = "\u001B[38;2;0;144;193m";
  private static final String RESET_COLOR = "\u001B[0m";

  private static final String BANNER =
      BLUE
          + "██      ███████  █████  ███████ ███████        █████         ██████  █████  ██████ \n"
          + "██      ██      ██   ██ ██      ██            ██   ██       ██      ██   ██ ██   ██ \n"
          + "██      █████   ███████ ███████ █████   █████ ███████ █████ ██      ███████ ██████ \n"
          + "██      ██      ██   ██      ██ ██            ██   ██       ██      ██   ██ ██   ██ \n"
          + "███████ ███████ ██   ██ ███████ ███████       ██   ██        ██████ ██   ██ ██   ██ \n"
          + SEA_GREEN
          + " \n When life gives you lemons, make vodka \n"
          + RESET_COLOR;

  @Override
  public void printBanner(Environment environment, Class<?> sourceClass, PrintStream out) {
    out.println(BANNER);
  }
}
