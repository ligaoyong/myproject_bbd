package com.spring5.Aop.transaction;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.springframework.context.annotation.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DelegatingDataSource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.web.context.annotation.RequestScope;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;

/**
 * 数据库配置
 */
@Configuration
@EnableAspectJAutoProxy
//@Import() 导入配置类
//@ImportResource 导入xml
//@PropertySource() 导入properties文件
public class DataBaseConfig {

    @Bean
    public DataSource dataSource(){
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/learn");
        dataSource.setUsername("root");
        dataSource.setPassword("root");
        return dataSource;
    }

    @RequestScope// 声明Bean的作用域为当前请求 每个请求都会创建新的Bean
    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource){
        return new JdbcTemplate(dataSource);
    }

    @Bean
    public PlatformTransactionManager transactionManager(DataSource dataSource){
        return new DataSourceTransactionManager(dataSource);
    }
}
