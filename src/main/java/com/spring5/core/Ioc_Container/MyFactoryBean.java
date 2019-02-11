package com.spring5.core.Ioc_Container;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;

@Component
public class MyFactoryBean implements FactoryBean<MyFactoryBean.MyBean> {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.scan("com.spring5");
        context.refresh();
        //获取bean(由FactoryBean创建)
        MyBean myBean = context.getBean(MyBean.class);
        System.out.println("myBean: "+myBean);
        MyBean myBean1 = context.getBean(MyBean.class);
        System.out.println("myBean1: "+myBean1);
        //获取FactoryBean本省
        MyFactoryBean myFactoryBean = (MyFactoryBean)context.getBean("&myFactoryBean");
        System.out.println("myFactoryBean: "+myFactoryBean);
    }

    @Override
    public MyBean getObject() throws Exception {
        System.out.println("调用方法创建bean----");
        return new MyBean("myBean_name");
    }

    @Override
    public Class<?> getObjectType() {
        return MyBean.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    //静态内部类
    static final class MyBean{
        private String name;

        public MyBean(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "MyBean{" +
                    "name='" + name + '\'' +
                    '}';
        }
    }
}
