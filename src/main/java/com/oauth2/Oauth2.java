package com.oauth2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.jdbc.DataSourceHealthIndicatorAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisReactiveAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.XADataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

/**
 * 测试Oauth2授权机制：
 *      使用github
 *      教程参考：http://www.ruanyifeng.com/blog/2019/04/github-oauth.html
 */
@SpringBootApplication(exclude = {
        DataSourceHealthIndicatorAutoConfiguration.class,
        DataSourceAutoConfiguration.class,DataSourceAutoConfiguration.class,
        XADataSourceAutoConfiguration.class,
        RedisAutoConfiguration.class, RedisReactiveAutoConfiguration.class})
@ComponentScan(value = "com.oauth2")
public class Oauth2 {

    public static void main(String[] args) {
        SpringApplication.run(Oauth2.class, args);
    }

}