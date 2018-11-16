package com.spring;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by wuyongchong on 2018/11/6.
 * spring源码入口类
 */
public class Main {
    public static void main(String[] args) {

        ClassPathXmlApplicationContext context =
                new ClassPathXmlApplicationContext("config.xml");
        SimpleBean bean = context.getBean(SimpleBean.class);

        bean.send();
        context.close();
    }

    //Assert断言工具 与guava的precondition一样
    @Test public void test(){
        org.springframework.util.Assert.state(1>1,"不正确的表达式");
    }
}
