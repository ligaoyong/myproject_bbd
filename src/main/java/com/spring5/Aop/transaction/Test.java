package com.spring5.Aop.transaction;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;

@Component
public class Test {

    @Resource
    private JdbcTemplate jdbcTemplate;

    @Transaction
    public void test() {
        jdbcTemplate.execute("insert into student values(11,'zhangsan')");

        /*Connection connection = DataSourceUtils.getConnection(jdbcTemplate.getDataSource());
        System.out.println("业务代码中的connection："+connection);*/
        int i = 1 / 0;

        jdbcTemplate.execute("insert into student values(12,'zhangsan')");

        System.out.println("sql执行完毕");
    }

}
