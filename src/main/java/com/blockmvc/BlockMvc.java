package com.blockmvc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.jdbc.DataSourceHealthIndicatorAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisReactiveAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.XADataSourceAutoConfiguration;

/**
 * 测试阻塞spring mvc得性能
 */
@SpringBootApplication(exclude = {
        DataSourceHealthIndicatorAutoConfiguration.class,
        DataSourceAutoConfiguration.class,DataSourceAutoConfiguration.class,
        XADataSourceAutoConfiguration.class,
        RedisAutoConfiguration.class, RedisReactiveAutoConfiguration.class})
public class BlockMvc {

    public static void main(String[] args) {
        SpringApplication.run(BlockMvc.class, args);
    }

}