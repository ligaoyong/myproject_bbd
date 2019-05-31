package com.redisson;

import org.redisson.api.RMap;
import org.redisson.api.RTransaction;
import org.redisson.spring.transaction.RedissonTransactionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;

/**
 * Redisson 与 Spring事务整合测试
 */
@Configuration
public class RedissonSpringTest {
    @Autowired
    private RedissonTransactionManager transactionManager;
    @Transactional
    public void commitData() {
        RTransaction transaction = transactionManager.getCurrentTransaction();
        RMap<String, String> map = transaction.getMap("test1");
        map.put("1", "2");
        transaction.commit();
    }
}
