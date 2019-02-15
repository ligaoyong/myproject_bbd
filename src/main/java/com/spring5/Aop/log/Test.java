package com.spring5.Aop.log;

import org.springframework.stereotype.Component;

@Component
public class Test {

    @Log(msg = "这是一个测试方法")
    public void testLog(){
        System.out.println(".........执行testLog方法中.....");
    }

    public void test(){
        System.out.println(".........执行普通方法.....");
    }

}
