package com.free.decision.server.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author Xingyf
 */
@Configuration
public class RedissonConfig {

    @Value("${spring.redis.host}")
    private String redisHost;

    @Value("${spring.redis.port}")
    private String redisPort;

    @Value("${spring.redis.password}")
    private String redisPassword;

    @Value("${spring.redis.timeout}")
    private int redisTimeout;

    @Value("${spring.redis.database}")
    private int redisDatabase;

    /**
     * 哨兵模式自动装配
     * @return
     */
    @Bean
    public RedissonClient redisson() {
        Config config = new Config();
        config.useSingleServer().setAddress("redis://".concat(redisHost).concat(":").concat(redisPort))
                .setPassword(redisPassword)
                .setTimeout(redisTimeout)
                .setDatabase(redisDatabase);
        return Redisson.create(config);
    }
}
