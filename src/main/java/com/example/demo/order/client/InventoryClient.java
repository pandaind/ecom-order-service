package com.example.demo.order.client;

import com.example.demo.order.config.LoadBalancerConfiguration;
import com.example.demo.order.model.Inventory;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "INVENTORY-SERVICE")
@LoadBalancerClient(name = "INVENTORY-SERVICE", configuration = LoadBalancerConfiguration.class)
public interface InventoryClient {
    @GetMapping("/api/inventories/{skuCode}")
    Inventory getInventory(@PathVariable("skuCode") String skuCode);

    @PutMapping(value = "/api/inventories/{skuCode}", params = {"quantity"})
    Inventory updateInventory(@PathVariable(value = "skuCode") final String skuCode,
                              @RequestParam(value = "quantity") final Long quantity);
}
