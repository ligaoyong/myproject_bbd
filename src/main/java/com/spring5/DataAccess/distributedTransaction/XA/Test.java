package com.spring5.DataAccess.distributedTransaction.XA;

import com.mysql.jdbc.jdbc2.optional.MysqlXid;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.XAConnection;
import javax.sql.XADataSource;
import javax.transaction.TransactionManager;

import javax.annotation.Resource;
import javax.transaction.xa.XAException;
import javax.transaction.xa.XAResource;
import javax.transaction.xa.Xid;
import java.sql.SQLException;
import java.sql.Statement;

@Component
public class Test {

    @Resource(name = "xaDataSource1")
    private XADataSource xaDataSource1;

    @Resource(name = "xaDataSource2")
    private XADataSource xaDataSource2;

    /**
     * 测试分布式事务:XA协议(2pc：2阶段提交)
     * XA协议存在数据不一致的情况(比如在commit之间发生异常)
     */
    public void test() throws SQLException, XAException {
        XAConnection xaConnection1 = xaDataSource1.getXAConnection();
        XAConnection xaConnection2 = xaDataSource2.getXAConnection();

        Statement statement1 = xaConnection1.getConnection().createStatement();
        Statement statement2 = xaConnection2.getConnection().createStatement();

        XAResource xaResource1 = xaConnection1.getXAResource();
        XAResource xaResource2 = xaConnection2.getXAResource();

        //标记一个事务的唯一id
        Xid xid1 = new MysqlXid(new byte[]{0x01},new byte[0x02],100);
        Xid xid2 = new MysqlXid(new byte[]{0x01},new byte[0x03],200);

        try {
            //开始一个事务分支 不标记任何东西
            xaResource1.start(xid1,XAResource.TMNOFLAGS);
            //执行sql
            statement1.execute("insert into student values(11,'zhangsan')");
            //标记一哥事务分支结束：标记成功
            xaResource1.end(xid1,XAResource.TMSUCCESS);
            //预提交
            int prepare1 = xaResource1.prepare(xid1);
            /**************************************************/
            //开始另外一个事务分支 不标记任何东西
            xaResource2.start(xid2,XAResource.TMNOFLAGS);
            //执行sql
            statement2.execute("insert into student2 values(11,'zhangsan')");
            //标记一哥事务分支结束：标记成功
            xaResource2.end(xid2,XAResource.TMSUCCESS);
            //预提交
            int prepare2 = xaResource2.prepare(xid2);

            //int i = 1 / 0;

            //如果预提交阶段都成功 则进行commit
            if (prepare1 == XAResource.XA_OK && prepare2 == XAResource.XA_OK){
                xaResource1.commit(xid1,false);
                // int i = 1 / 0; 在commit之间发生异常 会导致数据不一致
                xaResource2.commit(xid2,false);
            }else {
                xaResource1.rollback(xid1);
                xaResource2.rollback(xid2);
            }

        } catch (Exception e) {
            xaResource1.rollback(xid1);
            xaResource2.rollback(xid2);
        }

    }

}
