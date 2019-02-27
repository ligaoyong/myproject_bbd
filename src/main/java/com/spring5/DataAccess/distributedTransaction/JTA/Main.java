package com.spring5.DataAccess.distributedTransaction.JTA;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.transaction.xa.XAException;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException, XAException {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.scan("com.spring5.DataAccess.distributedTransaction.JTA");
        context.refresh();

        Test bean = context.getBean(Test.class);
        bean.test();
    }
}
