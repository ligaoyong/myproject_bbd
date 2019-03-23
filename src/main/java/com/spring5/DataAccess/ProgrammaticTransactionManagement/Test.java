package com.spring5.DataAccess.ProgrammaticTransactionManagement;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.Resource;

@Component
public class Test {

    @Resource
    private JdbcTemplate jdbcTemplate;

    @Resource
    private TransactionTemplate transactionTemplate;

    @Resource
    private PlatformTransactionManager transactionManager;

    /**
     * 编程式事务管理：TransactionTemplate实现
     */
    public void test() {
        transactionTemplate.execute(status -> {
            System.out.println("开始事务-----");
            jdbcTemplate.execute("insert into student values(11,'zhangsan')");
            /**
             * transactionTemplate能够处理抛出的RuntimeException，执行回滚
             * 如何catch了异常，transactionTemplate将会感知不到，不会执行回滚
             * 此时只有设置status.setRollbackOnly()才会回滚;
             */
            throw new RuntimeException("出现异常");
                /*jdbcTemplate.execute("insert into student values(12,'zhangsan')");
                System.out.println("结束事务-----");*/
        });
    }

    /**
     * 编程式事务管理：PlatformTransactionManager
     * 这种方式比较底层，不推荐使用
     */
    public void test1(){

        DefaultTransactionDefinition transactionDefinition = new DefaultTransactionDefinition();
        transactionDefinition.setTimeout(100);
        //调用doGetTransaction获取与当前线程、数据源相关的事物及连接
        TransactionStatus transactionStatus = transactionManager.getTransaction(transactionDefinition);

        try {
            System.out.println("开始事务-----");
            jdbcTemplate.execute("insert into student values(11,'zhangsan')");
            throw new RuntimeException("出现异常");
        } catch (RuntimeException e) {
            /**
             * 也可以执行transactionManager.rollback(transactionStatus);
             */
            transactionStatus.setRollbackOnly();
            e.printStackTrace();
        }
        //commit中会去判断要不要回滚 不用显示的执行rollback方法
        transactionManager.commit(transactionStatus);
    }
}
