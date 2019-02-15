package com.spring5.Aop.paramCheck;

import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

/**
 * 测试类
 */
@Component
@EnableAspectJAutoProxy
public class Test {

    public void test1(String name,@NotNull Integer age){
        System.out.println("执行方法....name : "+name+" age : "+age);
    }


}
