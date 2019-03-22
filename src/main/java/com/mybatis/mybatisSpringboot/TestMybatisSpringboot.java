package com.mybatis.mybatisSpringboot;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.*;


@MapperScan("com.mybatis.mybatisSpringboot") //创建MapperFactoryBean的BeanDefinition
@SpringBootApplication(scanBasePackages = {"com.mybatis.mybatisSpringboot"},
        //排除Jdbc相关的自动配置
        exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class,
                JdbcTemplateAutoConfiguration.class, JndiDataSourceAutoConfiguration.class,
                XADataSourceAutoConfiguration.class})
public class TestMybatisSpringboot {
    public static void main(String[] args){
        SpringApplication.run(TestMybatisSpringboot.class, args);
    }
}
