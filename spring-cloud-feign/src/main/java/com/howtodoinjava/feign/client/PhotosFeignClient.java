package com.howtodoinjava.feign.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "photosFeignClient", url = "https://jsonplaceholder.typicode.com/")
public interface PhotosFeignClient {

	@RequestMapping(method = RequestMethod.GET, value = "/photos")
	String getPhotos();
}
