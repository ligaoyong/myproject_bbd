package com.proxy.jdk;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Objects;

/**
 * TODO
 *
 * @author ligaoyong@gogpay.cn
 * @date 2019/10/30 14:42
 */
public class MyInvocationHandler implements InvocationHandler {

    private Object realObject;

    public MyInvocationHandler(Object realObject) {
        this.realObject = realObject;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //这里的proxy是生成的代理对象
        System.out.println("----------before-------");
        Object result = method.invoke(realObject, args);
        System.out.println("----------after--------");
        return result;
    }
}
