package com.mybatis;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.session.SqlSessionManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

/**
 * Mybatis的java配置
 */
public class MybatsiConfig {

    @Bean
    public DataSource dataSource(){
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/learn");
        dataSource.setUsername("root");
        dataSource.setPassword("root");
        return dataSource;
    }

    @Bean
    public PlatformTransactionManager transactionManager(DataSource dataSource){
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean
    public SqlSessionFactory sqlSessionFactory(@Qualifier("dataSource") DataSource dataSource){
        //使用同一的事务管理平台PlatformTransactionManager 只要数据源相同 不会影响事务的执行
        Environment environment = new Environment("development",null, dataSource);
        //Configuration对象相当于xml中的<configuration/>标签
        Configuration configuration = new Configuration(environment);
        /**
         * 如果存在一个对等的 XML 配置文件的话，MyBatis 会自动查找并加载它
         * （这种情况下， BlogMapper.xml 将会基于类路径和 BlogMapper.class 的类名被加载进来）
         */
        //configuration.addMapper(BlogMapper.class);
        return new SqlSessionFactoryBuilder().build(configuration);
        //另外一种SqlSessionFactory的实现
        //return SqlSessionManager.newInstance(Resources.getResourceAsStream("class:/xxxxx"));
    }


}
