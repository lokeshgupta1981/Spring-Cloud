package com.howtodoinjava.feign.controller;

import com.howtodoinjava.feign.client.AccountFeignClient;
import com.howtodoinjava.feign.client.PostsFeignClient;
import com.howtodoinjava.feign.dto.Post;
import com.howtodoinjava.feign.dto.User;
import feign.Feign;
import feign.gson.GsonDecoder;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FeignController {

  private PostsFeignClient postsFeignClient;

  @Autowired
  private AccountFeignClient accountFeignClient;

  @Autowired
  public FeignController() {
    this.postsFeignClient = Feign.builder().decoder(new GsonDecoder())
        .target(PostsFeignClient.class,
            "https://jsonplaceholder.typicode.com/");
  }

  @GetMapping(value = "/users")
  public List<User> getAllUsers() {
    return accountFeignClient.getUsers();
  }

  @GetMapping(value = "/posts")
  public List<Post> getAllPosts() {
    return postsFeignClient.getPosts();
  }

  @GetMapping(value = "/account/{userId}")
  public String getAccountByUserId(@PathVariable("userId") Integer userId) {
    return accountFeignClient.getAccountByUserId(userId);
  }
}
