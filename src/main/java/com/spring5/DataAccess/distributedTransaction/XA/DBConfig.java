package com.spring5.DataAccess.distributedTransaction.XA;

import com.mysql.jdbc.jdbc2.optional.MysqlXADataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.Resource;
import javax.sql.DataSource;
import javax.sql.XADataSource;

/**
 * 数据库配置
 */
@Configuration
public class DBConfig {

    //要使用分布式事务 必须使用XADataSource
    @Bean("xaDataSource1")
    public XADataSource xaDataSource(){
        MysqlXADataSource xaDataSource = new MysqlXADataSource();
        xaDataSource.setURL("jdbc:mysql://localhost:3306/learn");
        xaDataSource.setUser("root");
        xaDataSource.setPassword("root");
        return xaDataSource;
    }

    @Bean("xaDataSource2")
    public XADataSource xaDataSource2(){
        MysqlXADataSource xaDataSource = new MysqlXADataSource();
        xaDataSource.setURL("jdbc:mysql://localhost:3306/learn2");
        xaDataSource.setUser("root");
        xaDataSource.setPassword("root");
        return xaDataSource;
    }
}
