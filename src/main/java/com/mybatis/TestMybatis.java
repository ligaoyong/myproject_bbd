package com.mybatis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.*;

@SpringBootApplication(scanBasePackages = {"com.mybatis"},
        //排除Jdbc相关的自动配置
        exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class,
                JdbcTemplateAutoConfiguration.class, JndiDataSourceAutoConfiguration.class,
                XADataSourceAutoConfiguration.class})
public class TestMybatis {
    public static void main(String[] args){
        SpringApplication.run(TestMybatis.class, args);
    }
}
