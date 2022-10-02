package com.example.demo.order.client;

import com.example.demo.order.config.LoadBalancerConfiguration;
import com.example.demo.order.model.User;
import com.example.demo.order.service.dto.UserDTO;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("USER-SERVICE")
@LoadBalancerClient(name = "USER-SERVICE", configuration = LoadBalancerConfiguration.class)
public interface UserClient {
    @GetMapping(value = "/api/users/{id}")
    UserDTO getUserById(@PathVariable("id") Long id);
}
