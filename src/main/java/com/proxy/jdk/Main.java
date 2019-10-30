package com.proxy.jdk;

import java.lang.reflect.Proxy;

/**
 * TODO
 *
 * @author ligaoyong@gogpay.cn
 * @date 2019/10/30 14:41
 */
public class Main {
    public static void main(String[] args) {
        HelloImpl hello = new HelloImpl();

        Hello proxy = (Hello)Proxy.newProxyInstance(hello.getClass().getClassLoader(),
                hello.getClass().getInterfaces(),
                new MyInvocationHandler(hello));

        proxy.sayHello("aaa");
        proxy.sayHello2("bbb");
    }
}
