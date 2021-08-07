package com.example.demo.order.config.redis;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
public class RedisProperties {
    private int redisPort;
    private String redisHost;

    public RedisProperties(@Value("${spring.redis.host}") String redisHost,
                           @Value("${spring.redis.port}") int redisPort) {
        this.redisPort = redisPort;
        this.redisHost = redisHost;
    }
}
