package com.spring5.Aop.transaction;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.scan("com.spring5.Aop.transaction");
        context.refresh();

        Test bean = context.getBean(Test.class);
        bean.test();
    }
}
