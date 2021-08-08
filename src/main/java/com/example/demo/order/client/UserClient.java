package com.example.demo.order.client;

import com.example.demo.order.model.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "User", url = "http://localhost:8082/api")
public interface UserClient {
    @GetMapping(value = "/users/{id}")
    User getUserById(@PathVariable("id") Long id);
}
