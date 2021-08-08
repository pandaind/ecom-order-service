package com.example.demo.order.client;

import com.example.demo.order.config.LoadBalancerConfiguration;
import com.example.demo.order.model.User;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("USER-SERVICE")
@LoadBalancerClient(name = "USER-SERVICE", configuration = LoadBalancerConfiguration.class)
public interface UserClient {
    @GetMapping(value = "/api/users/{id}")
    User getUserById(@PathVariable("id") Long id);
}
