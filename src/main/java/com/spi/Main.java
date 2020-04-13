package com.spi;

import java.util.Iterator;
import java.util.ServiceLoader;

public class Main {
    public static void main(String[] args) {
        //加载所有MyInterface的实现类(通常是框架来进行，这样就能加载到用户实现的类)
        //但实现类要声明在类路径下的META-INF/services,且文件名称一定要是接口的全限定名，内容是实现类的全限定名
        /**
         * mysql的驱动加载正是这种SPI机制，详细可看mysql-connector-java.jar
         */
        ServiceLoader<MyInterface> serviceLoader = ServiceLoader.load(MyInterface.class);
        Iterator<MyInterface> iterator = serviceLoader.iterator();
        while (iterator.hasNext()){
            MyInterface next = iterator.next();
            System.out.println("实现类："+next.getClass()); //实现类： com.spi.MyImpl
        }
    }
}
