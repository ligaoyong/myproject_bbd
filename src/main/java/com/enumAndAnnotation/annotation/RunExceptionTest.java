package com.enumAndAnnotation.annotation;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by lgy on 2018/11/16.
 * 运行测试
 */
public class RunExceptionTest {
    public static void main(String[] args) throws ClassNotFoundException, InvocationTargetException, IllegalAccessException, InstantiationException {
        Class<?> aClass = Class.forName("com.enumAndAnnotation.annotation.ExceptionTest1");
        Object o = aClass.newInstance();
        for (Method method:aClass.getDeclaredMethods()) {
            if(method.isAnnotationPresent(ExceptionTest.class)){
                //获取注解中的值
                Class<? extends Throwable>[] value = method.getAnnotation(ExceptionTest.class).value();
                try {
                    method.invoke(o, null);
                } catch (InvocationTargetException e) {
                    //e.getCause().printStackTrace();
                    int pass = 0;
                    for (Class exc:value) {
                        if (exc.isInstance(e.getCause())) {
                            System.out.println(method.getName() + " : pass");
                            pass++;
                            break;
                        }
                    }
                    if (pass==0){
                        System.out.println(method.getName()+ " : fail");
                    }
                }
            }
        }
    }
}
