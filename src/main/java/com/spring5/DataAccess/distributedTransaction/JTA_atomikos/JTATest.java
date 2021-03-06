package com.spring5.DataAccess.distributedTransaction.JTA_atomikos;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.jta.JtaTransactionManager;

import javax.annotation.Resource;
import javax.transaction.*;

@Service("JTATest")
public class JTATest {

    @Resource(name = "jdbcTemplate1")
    private JdbcTemplate jdbcTemplate1;

    @Resource(name = "jdbcTemplate2")
    private JdbcTemplate jdbcTemplate2;

    @Resource
    private UserTransaction userTransaction;

    /**
     * 测试分布式事务: JTA_atomikos(XA)实现：并发性较差，不适用于高并发情况
     * 说明：
     * JTA使用的也是XA协议及2PC(两阶段提交：prepare commit)
     * JTA只是一个java api，主要是UserTransaction、TransactionManager、ResourceManager三个接口
     * JTA有着各种各样的实现，比如：JOTM、atomikos等
     * 一般而言，我们只需要配置UserTransaction的实现、TransactionManager的实现即可，ResourceManager的实现则是
     * 各大数据库、JSM提供商实现，无需我们管
     * 使用：直接使用UserTransaction即可！(也就是只需要创建UserTransaction即可)
     */
    public void test() throws SystemException, NotSupportedException, HeuristicRollbackException, HeuristicMixedException, RollbackException {
//        UserTransaction userTransaction = jtaTransactionManager.getUserTransaction();
        //只是用UserTransaction也是可以的 还很方便
        userTransaction.begin();
        try {
            /**
             * jdbcTemplate1 是通过XA数据源来创建的 执行时会检查是否有分布式事务(通过userTransaction关联到上下文)
             */
            jdbcTemplate1.execute("insert into student values(12,'zhangsan')");
            //int i = 1 / 0; //测试通过 回滚完成
            jdbcTemplate2.execute("insert into student2 values(12,'zhangsan')");
            //int i = 1 / 0;    //测试通过  回滚完成
        } catch (Exception e) {
            userTransaction.rollback();
            e.printStackTrace();
            return;
        }
        userTransaction.commit();
    }

    //不生效是因为没有被扫描进去，不是springboot项目
    @Transactional(transactionManager = "jtaTransactionManager",rollbackFor = Throwable.class) //与spring整合
    public void test2() {
        jdbcTemplate1.execute("insert into student values(13,'李四')");
        int i = 1 / 0; //测试通过 回滚完成
        jdbcTemplate2.execute("insert into student2 values(13,'李四')");

    }

}
