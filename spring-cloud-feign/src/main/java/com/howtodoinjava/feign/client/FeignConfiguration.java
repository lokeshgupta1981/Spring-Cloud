package com.howtodoinjava.feign.client;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.support.BasicAuthenticationInterceptor;

import feign.Logger;
import feign.Retryer;
import feign.codec.ErrorDecoder;

@Configuration
public class FeignConfiguration {

	@Bean
	Logger.Level feignLoggerLevel() {
		return Logger.Level.BASIC;
	}

	/*
	 * @Bean RequestInterceptor requestInterceptor() { return requestTemplate -> {
	 * requestTemplate.header("requestID", "UUID"); }; }
	 */

	@Bean
	BasicAuthenticationInterceptor basicAuthenticationInterceptor() {
		return new BasicAuthenticationInterceptor("admin", "admin");
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
