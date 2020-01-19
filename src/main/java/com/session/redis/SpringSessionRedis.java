package com.session.redis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.jdbc.DataSourceHealthIndicatorAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.XADataSourceAutoConfiguration;

/**
 * spring seesion + redis
 */
@SpringBootApplication(exclude = {
        DataSourceHealthIndicatorAutoConfiguration.class,
        DataSourceAutoConfiguration.class,DataSourceAutoConfiguration.class,
        XADataSourceAutoConfiguration.class})
public class SpringSessionRedis {

    public static void main(String[] args) {
        SpringApplication.run(SpringSessionRedis.class, args);
    }

}