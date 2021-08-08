package com.example.demo.order.config;

import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoadBalancerConfiguration {
    @Bean
    public ServiceInstanceListSupplier discoveryClientServiceInstanceListSupplier(ConfigurableApplicationContext context) {
        // Configuring Load balancer to prefer same instance
        return ServiceInstanceListSupplier.builder()
                .withBlockingDiscoveryClient()
                //.withSameInstancePreference()
                .build(context);
    }
}
