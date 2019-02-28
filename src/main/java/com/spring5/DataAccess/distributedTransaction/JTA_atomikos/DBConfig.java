package com.spring5.DataAccess.distributedTransaction.JTA_atomikos;

import com.atomikos.icatch.jta.UserTransactionImp;
import com.atomikos.icatch.jta.UserTransactionManager;
import com.atomikos.jdbc.AtomikosDataSourceBean;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.jta.JtaTransactionManager;

import javax.sql.DataSource;
import javax.transaction.UserTransaction;
import java.util.Properties;

/**
 * 数据库配置
 */
@Configuration
public class DBConfig {

    /**
     * AtomikosDataSourceBean为您处理资源的注册和注销。您不必调用像enlistResource和delistResource这样的事务方法。
     * 您也不必关心恢复，因为这也是完全透明地处理的。只要记住，如果使用AtomikosDataSourceBean,
     * XA的所有复杂性都会得到透明的处理。
     *
     * @return
     */
    @Bean("atomikosDataSourceBean1")
    public AtomikosDataSourceBean atomikosDataSourceBean1() {
        AtomikosDataSourceBean atomikosDataSourceBean = new AtomikosDataSourceBean();
        atomikosDataSourceBean.setUniqueResourceName("mysql1");
        atomikosDataSourceBean.setXaDataSourceClassName("com.mysql.jdbc.jdbc2.optional.MysqlXADataSource");
        Properties p = new Properties();
        p.setProperty("user", "root");
        p.setProperty("password", "root");
        p.setProperty("URL", "jdbc:mysql://localhost:3306/learn");
        atomikosDataSourceBean.setXaProperties(p);
        atomikosDataSourceBean.setPoolSize(5);
        return atomikosDataSourceBean;
    }

    @Bean
    public JdbcTemplate jdbcTemplate1(@Qualifier("atomikosDataSourceBean1")DataSource dataSource){
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        jdbcTemplate.setDataSource(dataSource);
        return jdbcTemplate;
    }

    @Bean("atomikosDataSourceBean2")
    public AtomikosDataSourceBean atomikosDataSourceBean2() {
        AtomikosDataSourceBean atomikosDataSourceBean = new AtomikosDataSourceBean();
        atomikosDataSourceBean.setUniqueResourceName("mysql2");
        atomikosDataSourceBean.setXaDataSourceClassName("com.mysql.jdbc.jdbc2.optional.MysqlXADataSource");
        Properties p = new Properties();
        p.setProperty("user", "root");
        p.setProperty("password", "root");
        p.setProperty("URL", "jdbc:mysql://localhost:3306/learn2");
        atomikosDataSourceBean.setXaProperties(p);
        atomikosDataSourceBean.setPoolSize(5);
        return atomikosDataSourceBean;
    }

    @Bean
    public JdbcTemplate jdbcTemplate2(@Qualifier("atomikosDataSourceBean2")DataSource dataSource){
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        jdbcTemplate.setDataSource(dataSource);
        return jdbcTemplate;
    }

    /**
     * 客户端程序员直接使用JtaTransactionManager即可，而不用管TransactionManager、UserTransaction的
     * 具体实现
     * 也可以不用JtaTransactionManager，直接使用UserTransaction的实现也可以
     * @param userTransactionManager
     * @param userTransaction
     * @return
     */
    @Bean
    public JtaTransactionManager jtaTransactionManager(
            @Qualifier("AtomikosTransactionManager") UserTransactionManager userTransactionManager,
            @Qualifier("AtomikosUserTransaction") UserTransaction userTransaction){
        JtaTransactionManager jtaTransactionManager = new JtaTransactionManager();
        jtaTransactionManager.setTransactionManager(userTransactionManager);//设置事务管理器
        jtaTransactionManager.setUserTransaction(userTransaction);//设置用户事务
        return jtaTransactionManager;
    }

    @Bean("AtomikosTransactionManager")
    public UserTransactionManager UserTransactionManager(){
        UserTransactionManager userTransactionManager = new UserTransactionManager();
        userTransactionManager.setForceShutdown(false);
        return userTransactionManager;
    }

    /**
     * 客户端程序员 也可以直接注入UserTransaction使用
     * UserTransaction的一切事物操作会委托给TransactionManager，而TransactionManager又会委托给具体的ResourceManager
     * 所以JTA(XA)的分布式执行流程为：
     *  UserTransaction------>TransactionManager------>ResourceManager
     * @return
     */
    @Bean("AtomikosUserTransaction")
    public UserTransaction userTransaction(){
        return new UserTransactionImp();
    }
}
