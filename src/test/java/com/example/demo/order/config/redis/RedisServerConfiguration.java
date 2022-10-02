package com.example.demo.order.config.redis;

import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ActiveProfiles;
import redis.embedded.RedisServer;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Configuration
@ActiveProfiles(value = {"test"})
public class RedisServerConfiguration {
    private final RedisServer redisServer;

    public RedisServerConfiguration(RedisProperties redisProperties) {
        this.redisServer = new RedisServer(redisProperties.getPort());
    }

    @PostConstruct
    public void postConstruct() {
        this.redisServer.start();
    }

    @PreDestroy
    public void preDestroy() {
        redisServer.stop();
    }
}
