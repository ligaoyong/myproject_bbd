package com.spring5.core.Ioc_Container;

import org.springframework.beans.factory.BeanNameAware;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.Lifecycle;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class MyLifecycle implements Lifecycle, BeanNameAware {

    public static void main(String[] args) {
        /**
         * 基于注解配置的ApplicationContext
         */
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext("com.spring5");
        //现实启动容器 start()方法会执行
        context.start();
        //不会调用销毁方法
        context.stop();
        //context.registerShutdownHook();
    }

    @PostConstruct
    public void init(){
        System.out.println("------init----------");
    }

    /**
     * 容器自动启动时不会执行 只有现实指定时才有用
     */
    @Override
    public void start() {
        System.out.println("container start.......");
    }

    /**
     * 不保证容器销毁之前会执行
     */
    @Override
    public void stop() {
        System.out.println("container stop.......");
    }

    @Override
    public boolean isRunning() {
        return false;
    }

    @Override
    public void setBeanName(String s) {
        System.out.println("beanName = "+s);
    }
}
