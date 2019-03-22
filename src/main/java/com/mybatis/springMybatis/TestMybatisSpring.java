package com.mybatis.springMybatis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.*;

@SpringBootApplication(scanBasePackages = {"com.mybatis.springMybatis"},
        //排除Jdbc相关的自动配置
        exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class,
                JdbcTemplateAutoConfiguration.class, JndiDataSourceAutoConfiguration.class,
                XADataSourceAutoConfiguration.class})
public class TestMybatisSpring {
    public static void main(String[] args){
        SpringApplication.run(TestMybatisSpring.class, args);
    }
}
