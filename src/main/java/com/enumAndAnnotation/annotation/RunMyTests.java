package com.enumAndAnnotation.annotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by lgy on 2018/11/16.
 * 运行被@MyTest注释的方法
 */
public class RunMyTests {
    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException, InvocationTargetException {

        Class<?> testClass = Class.forName("com.enumAndAnnotation.annotation.Sample");
        Object o = testClass.newInstance();
        //获取所有方法
        for (Method method:testClass.getDeclaredMethods()) {
            //方法上是否有MyTest注解
            if (method.isAnnotationPresent(MyTest.class)){
                System.out.println(method.getName());
                try {
                    method.invoke(o, null);
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        }
    }
}
