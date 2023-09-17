package com.example.springawssqsexample;

import com.example.springawssqsexample.service.MessageSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringAwsSqsExampleApplication implements CommandLineRunner {

  @Autowired
  private MessageSender messageSender;

  public static void main(String[] args) {
    SpringApplication.run(SpringAwsSqsExampleApplication.class, args);
  }

  @Override
  public void run(String... args) throws Exception {
    messageSender.sendMessage("Hello World");
  }
}
