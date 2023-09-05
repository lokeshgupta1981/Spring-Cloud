package com.howtodoinjava.feign.client;

import com.howtodoinjava.feign.dto.User;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "userFeignClient", url = "https://jsonplaceholder.typicode.com/")
public interface UserFeignClient {

  @RequestMapping(method = RequestMethod.GET, value = "/users")
  ResponseEntity<List<User>> getUsers();
}
