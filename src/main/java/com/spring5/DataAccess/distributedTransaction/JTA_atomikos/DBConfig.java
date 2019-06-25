package com.spring5.DataAccess.distributedTransaction.JTA_atomikos;

import com.atomikos.icatch.jta.UserTransactionImp;
import com.atomikos.icatch.jta.UserTransactionManager;
import com.atomikos.jdbc.AtomikosDataSourceBean;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.PlatformTransactionManager;
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
     * 客户端程序员直接使用JtaTransactionManager(作为spring事务管理器)即可，而不用管TransactionManager、UserTransaction的
     * 具体实现
     * 也可以不用JtaTransactionManager，直接使用UserTransaction的实现也可以
     * @param userTransaction
     * @return
     */
    // 也不用创建  直接使用UserTransaction即可 但是要整合到spring中(如使用@Transactional)时，就需要手动创建
    // 且必须设置UserTransaction，但不必创建UserTransactionManager(UserTransaction会创建)
    // 此时JtaTransactionManager就作为一个事务管理器 在@Transactional中指定使用这个事务管理器即可
    @Bean("jtaTransactionManager")
    public JtaTransactionManager jtaTransactionManager(
            @Qualifier("AtomikosUserTransaction") UserTransaction userTransaction){
        JtaTransactionManager jtaTransactionManager = new JtaTransactionManager();
        jtaTransactionManager.setUserTransaction(userTransaction);//设置用户事务
        return jtaTransactionManager;
    }

//    // 不用创建 UserTransaction会帮我们自动创建
//    @Bean("AtomikosTransactionManager")
//    public UserTransactionManager UserTransactionManager(){
//        UserTransactionManager userTransactionManager = new UserTransactionManager();
//        userTransactionManager.setForceShutdown(false);
//        return userTransactionManager;
//    }

    /**
     * 客户端程序员 也可以直接注入UserTransaction使用
     * UserTransaction的一切事物操作会委托给TransactionManager，而TransactionManager又会委托给具体的ResourceManager
     * 所以JTA(XA)的分布式执行流程为：
     *  UserTransaction------>TransactionManager(UserTransaction会自动创建)------>ResourceManager(XAResource接口，提供商实现)
     *  这里的TransactionManager不是spring的事务管理器，而是DTP模型中的TM，不是一回事
     * @return
     */
    @Bean("AtomikosUserTransaction")
    public UserTransaction userTransaction(){
        return new UserTransactionImp();
    }
}
