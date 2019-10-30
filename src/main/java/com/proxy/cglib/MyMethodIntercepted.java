package com.proxy.cglib;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * TODO
 *
 * @author ligaoyong@gogpay.cn
 * @date 2019/10/30 14:56
 */
public class MyMethodIntercepted implements MethodInterceptor {

    @Override
    public Object intercept(Object proxyObj, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        /**
         * 这里的proxyObj是通过enhance.create创建出来的代理对象
         */
        System.out.println("--------before-------");
        Object result = methodProxy.invokeSuper(proxyObj, args);
        System.out.println("--------after-------");
        return result;
    }
}
