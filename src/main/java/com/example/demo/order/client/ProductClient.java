package com.example.demo.order.client;

import com.example.demo.order.config.LoadBalancerConfiguration;
import com.example.demo.order.model.Product;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("PRODUCT-SERVICE")
@LoadBalancerClient(name = "PRODUCT-SERVICE", configuration = LoadBalancerConfiguration.class)
public interface ProductClient {
    @GetMapping(value = "/api/products/{id}")
    Product getProductById(@PathVariable(value = "id") Long productId);
}
