package com.spring5.DataAccess.distributedTransaction.JTA;

import com.mysql.jdbc.jdbc2.optional.MysqlXid;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.sql.XAConnection;
import javax.sql.XADataSource;
import javax.transaction.xa.XAException;
import javax.transaction.xa.XAResource;
import javax.transaction.xa.Xid;
import java.sql.SQLException;
import java.sql.Statement;

@Component
public class Test {

    /**
     * 测试分布式事务: JTA实现
     */
    public void test() throws SQLException, XAException {

    }

}
