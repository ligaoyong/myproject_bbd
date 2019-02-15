package com.spring5.Aop.paramCheck;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.scan("com.spring5.Aop.paramCheck");
        context.refresh();

        Test bean = context.getBean(Test.class);
        bean.test1(null,15);

        bean.test1(null,null);
    }
}
