package com.group.libraryapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource("classpath:properties/env.properties")
public class LibraryAppApplication {

  public static void main(String[] args) {

    SpringApplication.run(LibraryAppApplication.class, args);
  }

}
