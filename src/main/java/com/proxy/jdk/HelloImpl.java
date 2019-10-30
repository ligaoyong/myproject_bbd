package com.proxy.jdk;

/**
 * TODO
 *
 * @author ligaoyong@gogpay.cn
 * @date 2019/10/30 14:37
 */
public class HelloImpl implements Hello {

    @Override
    public void sayHello(String name) {
        System.out.println("hello "+name +", 我是通过接口继承的方法");
    }

    /**
     * 使用jdk ：这里的方法就算是final也可以代理 只要是通过接口继承来的
     * @param name
     */
    @Override
    public final void sayHello2(String name) {
        System.out.println("hello "+name +", 我是通过接口继承的方法,且我是一个final方法");
    }

    /**
     * 使用jdk : 这里的方法不是通过接口继承来的 不能够被代理 只能使用cglib
     * @param name
     */
    public void sayHello3(String name){
        System.out.println("hello " + name + ", 我不是通过接口继承的方法");
    }
}
