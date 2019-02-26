package com.spring5.Aop.transaction;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * 处理注解@Transaction的方面
 */
@Aspect
@Component
public class AnnotationTransActionAspect {

    @Resource
    private DataSourceTransactionManager transactionManager;

    @Pointcut("within(com.spring5.Aop.transaction..*) && @annotation(com.spring5.Aop.transaction.Transaction)")
    public void pointcut(){}


    /**
     * 非常重要 建议反复研究 特别是TransactionSynchronizationManager(事务同步管理器)
     * @param joinPoint
     * @return
     * @throws SQLException
     */
    @Around(value = "pointcut()")
    public Object transcation(ProceedingJoinPoint joinPoint) throws SQLException {

        DataSource dataSource = transactionManager.getDataSource();
        //dataSource.getConnection()拿到的连接不是当前线程使用的连接 会导致事务不生效
        //Connection connection = dataSource.getConnection();
        /**
         * 同一个事务中 对同一个数据库(狭义只同一台机子)只能允许使用同一个连接！！！！！！！！
         * 这样才能保证事务提交、事务回滚生效
         * 详细：https://blog.csdn.net/oTengYue/article/details/51145990
         */

        /**
         * 要想Aop手动控制事务 则在一个事务中必须拿到同一个Connection对象
         * 这必须依赖于TransactionSynchronizationManager(使用ThreadLocal持有当前线程的Connection)
         * 所以初始化同步、注册一个同步这两部必须要做
         */
        //初始化同步
        TransactionSynchronizationManager.initSynchronization();
        //注册一个同步（本质上是一个事务执行过程中的回调接口）
        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter() {
            @Override
            public void afterCommit() {
                super.afterCommit();
            }
        });
        /**
         * DataSourceUtils.getConnection会拿到当前线程使用的连接
         * 原理请看TransactionSynchronizationManager(事务同步管理器)源码
         */
        Connection connection = DataSourceUtils.getConnection(dataSource);
        connection.setAutoCommit(false);
        Object reval = null;
        System.out.println("Aop事务中的connection："+connection);

        try{
            reval = joinPoint.proceed();
            connection.commit();
        }catch (Throwable throwable){
            connection.rollback();
        }
        return reval;
    }

}
