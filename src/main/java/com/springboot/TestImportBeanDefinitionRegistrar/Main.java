package com.springboot.TestImportBeanDefinitionRegistrar;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.jdbc.DataSourceHealthIndicatorAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.XADataSourceAutoConfiguration;

@SpringBootApplication(exclude = {
        DataSourceHealthIndicatorAutoConfiguration.class,
        DataSourceAutoConfiguration.class,DataSourceAutoConfiguration.class,
        XADataSourceAutoConfiguration.class})
public class Main {

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

}
