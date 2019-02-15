package com.spring5.Aop.log;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext("com.spring5.Aop.log");

        //context.refresh();

        Test bean = context.getBean(Test.class);
        bean.testLog(); //有方面增强
        System.out.println();
        System.out.println();
        bean.test();    //无方面增强
    }
}
