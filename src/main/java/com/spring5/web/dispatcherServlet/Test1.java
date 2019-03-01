package com.spring5.web.dispatcherServlet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.*;

@SpringBootApplication(scanBasePackages = {"com.spring5.web.dispatcherServlet"},
        //排除Jdbc相关的自动配置
        exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class,
                JdbcTemplateAutoConfiguration.class, JndiDataSourceAutoConfiguration.class,
                XADataSourceAutoConfiguration.class})
public class Test1 {
    public static void main(String[] args){
        SpringApplication.run(Test1.class, args);
    }
}
