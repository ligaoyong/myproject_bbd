package com.spring5.DataAccess.distributedTransaction.JTA;

import com.mysql.jdbc.jdbc2.optional.MysqlXADataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
