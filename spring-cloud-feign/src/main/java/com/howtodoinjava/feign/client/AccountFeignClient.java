package com.howtodoinjava.feign.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "accountFeignClient", url = "https://jsonplaceholder.typicode.com/", configuration = {
		AccountFeignConfiguration.class/* , OAuthFeignConfig.class */})
public interface AccountFeignClient extends UserFeignClient {

	@RequestMapping(method = RequestMethod.GET, value = "/users/{userId}")
	String getAccountByUserId(@PathVariable("userId") Integer userId);

}
