package com.howtodoinjava.feign.client;

import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.springframework.context.annotation.Bean;

public class AccountFeignConfiguration {

	@Bean
	public CloseableHttpClient feignClient() {
		return HttpClients.createDefault();
	}

}
