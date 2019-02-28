package com.spring5.DataAccess.distributedTransaction.JTA_atomikos;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import javax.transaction.*;

public class Main {
    public static void main(String[] args) throws HeuristicRollbackException, HeuristicMixedException, NotSupportedException, RollbackException, SystemException {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.scan("com.spring5.DataAccess.distributedTransaction.JTA_atomikos");
        context.refresh();

        JTATest bean = (JTATest) context.getBean("JTATest");
        bean.test();
    }
}
