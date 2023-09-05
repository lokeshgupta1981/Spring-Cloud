package com.howtodoinjava.feign.config;

import feign.Logger;
import feign.RequestInterceptor;
import feign.Retryer;
import feign.codec.ErrorDecoder;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.support.BasicAuthenticationInterceptor;

@EnableFeignClients(basePackages="com.howtodoinjava.feign.client")
@Configuration
public class FeignConfig {

  @Bean
  RequestInterceptor requestInterceptor() {
    return requestTemplate -> {
      requestTemplate.header("requestID", "UUID");
    };
  }

  @Bean
  Logger.Level feignLoggerLevel() {
    return Logger.Level.BASIC;
  }

  @Bean
  BasicAuthenticationInterceptor basicAuthenticationInterceptor() {
    return new BasicAuthenticationInterceptor("admin", "password");
  }

  @Bean
  ErrorDecoder errorDecoder() {
    return new CustomErrorDecoder();
  }

  @Bean
  Retryer customRetryer() {
    return new CustomRetryer(5, 2000L);
  }
}
