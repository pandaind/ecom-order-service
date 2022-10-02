package com.example.demo.order.config.redis;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "spring.redis")
public class RedisProperties {
    private int port;
    private String host;
}
