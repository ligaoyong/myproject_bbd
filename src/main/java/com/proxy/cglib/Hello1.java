package com.proxy.cglib;

/**
 * TODO
 *
 * @author ligaoyong@gogpay.cn
 * @date 2019/10/30 14:37
 */
public class Hello1{

    public void sayHello(String name) {
        System.out.println("hello "+name);
    }

    /**
     * 使用cglib代理 ：代理不会生效 只会执行方法 而不会执行方法的前后增强
     * @param name
     */
    public final void sayHello2(String name) {
        System.out.println("hello "+name +" 我是一个final方法");
    }

    /**
     *
     * @param name
     */
    public void sayHello3(String name){
        System.out.println("hello " + name);
    }
}
