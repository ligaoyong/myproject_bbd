package com.mybatis.springMybatis;

import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.PlatformTransactionManager;
import javax.sql.DataSource;
import java.io.IOException;

/**
 * Mybatis-spring整合的java配置
 * 对比Mybatis的配置
 */
@Configuration
public class BatisSpringConfig {

    @Bean
    public DataSource dataSource(){
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        //dataSource.setUrl("jdbc:mysql://localhost:3306/learn");
        dataSource.setUrl("jdbc:mysql://120.78.74.253:3306/learn");//阿里云数据库
        dataSource.setUsername("root");
        dataSource.setPassword("123456");
        return dataSource;
    }

    @Bean
    public PlatformTransactionManager transactionManager(DataSource dataSource){
        return new DataSourceTransactionManager(dataSource);
    }


    /**
     * mybatis与spring的整合中 只需要配置SqlSessionFactoryBean、MapperScannerConfigurer即可
     * 与mybatis的SqlSessionFactory不同了
     *      SqlSessionFactoryBean是一个用于生产SqlSessionFactory的FactoryBean
     * @return
     */
    @Bean
    public SqlSessionFactoryBean sqlSessionFactoryBean(DataSource dataSource) throws IOException {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        //指定数据源配置 必须的
        sqlSessionFactoryBean.setDataSource(dataSource);
        //指定mybatis配置 可选的
        sqlSessionFactoryBean.setConfiguration(new org.apache.ibatis.session.Configuration());
        //指定xml映射文件的位置 可选的(没有xml映射文件时)
        sqlSessionFactoryBean.setMapperLocations(
                new PathMatchingResourcePatternResolver().getResources("classpath:mapper/*.xml"));
        return sqlSessionFactoryBean;
    }

    //配置mapper扫描器 只扫描接口
    @Bean
    public MapperScannerConfigurer mapperScannerConfigurer(){
        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
        mapperScannerConfigurer.setBasePackage("com.mybatis.springMybatis");
        /**
         * MapperScannerConfigurer实现了BeanDefinitionRegistryPostProcessor
         * 而BeanDefinitionRegistryPostProcessor实现了BeanFactoryPostProcessor
         * 也就是说MapperScannerConfigurer实现了BeanFactoryPostProcessor
         * 因此在加载MapperScannerConfigurer的BeanDefinition的时候会扫描接口，
         * 并加载接口的代理BeanDefinition：MapperFactoryBean，
         * 由此可知 mapper接口的动态代理MapperFactoryBean是由MapperScannerConfigurer创建的
         */
        return mapperScannerConfigurer;
    }

    /**
     * 创建Dao接口对应的mapper(不用 只用于了解底层，
     * 实际应用中配置MapperScannerConfigurer来创建MapperFactoryBean)
     * MapperFactoryBean用于产生接口对应的MapperProxy
     */
    /*@Bean
    public MapperFactoryBean dao(SqlSessionFactory sqlSessionFactory){
        //SqlSessionFactory由SqlSessionFactoryBean产生
        MapperFactoryBean<Dao> daoMapperFactoryBean = new MapperFactoryBean<>();
        daoMapperFactoryBean.setMapperInterface(Dao.class);
        daoMapperFactoryBean.setSqlSessionFactory(sqlSessionFactory);
        return daoMapperFactoryBean;
    }*/
}
