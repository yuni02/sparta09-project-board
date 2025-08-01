package com.fastcampus.sparta09projectboard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@ConfigurationPropertiesScan
@SpringBootApplication
public class Sparta09ProjectBoardApplication {

  public static void main(String[] args) {
    SpringApplication.run(Sparta09ProjectBoardApplication.class, args);
  }
}
