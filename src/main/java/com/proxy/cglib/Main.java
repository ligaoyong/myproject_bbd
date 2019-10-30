package com.proxy.cglib;

import com.proxy.jdk.HelloImpl;
import net.sf.cglib.proxy.Enhancer;

/**
 * TODO
 *
 * @author ligaoyong@gogpay.cn
 * @date 2019/10/30 15:16
 */
public class Main {
    public static void main(String[] args) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(Hello1.class);
        enhancer.setCallback(new MyMethodIntercepted());

        Hello1 hello = (Hello1)enhancer.create();
        hello.sayHello("aaa");
        hello.sayHello2("bbb");
        hello.sayHello3("ccc");
    }
}
