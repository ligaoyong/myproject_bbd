package com.spring5.DataAccess.ProgrammaticTransactionManagement;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.web.context.annotation.RequestScope;

import javax.sql.DataSource;

/**
 * 数据库配置
 */
@Configuration
public class DBConfig {

    @Bean
    public DataSource dataSource(){
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/learn");
        dataSource.setUsername("root");
        dataSource.setPassword("root");
        return dataSource;
    }

    //@RequestScope// 声明Bean的作用域为当前请求 每个请求都会创建新的Bean
    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource){
        return new JdbcTemplate(dataSource);
    }

    /**
     * 其中的doGetTransaction方法使用了TransactionSynchronizationManager(事务的核心底层类)
     * @param dataSource
     * @return
     */
    @Bean
    public PlatformTransactionManager transactionManager(DataSource dataSource){
        return new DataSourceTransactionManager(dataSource);
    }

    /**
     * 事务模板，比DataSourceTransactionManager更加高级的一种事务控制方式
     * DataSourceTransactionManager比较底层
     * @param platformTransactionManager
     * @return
     */
    @Bean
    public TransactionTemplate transactionTemplate(PlatformTransactionManager platformTransactionManager){
        return new TransactionTemplate(platformTransactionManager);
    }
}
