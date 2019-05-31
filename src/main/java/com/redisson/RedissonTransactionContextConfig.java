package com.redisson;

import org.redisson.api.RMap;
import org.redisson.api.RTransaction;
import org.redisson.api.RedissonClient;
import org.redisson.spring.transaction.RedissonTransactionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PreDestroy;

/**
 * Redisson事务与Spring事务的整合
 */
@Configuration
@EnableTransactionManagement
public class RedissonTransactionContextConfig {

    @Bean
    public RedissonTransactionManager transactionManager(RedissonClient redisson) {
        return new RedissonTransactionManager(redisson);
    }

    @Bean
    public RedissonClient redisson() {
        return RedissonConfig.newSingleInstance();
    }

    @PreDestroy
    public void destroy() {
        redisson().shutdown();
    }
}
