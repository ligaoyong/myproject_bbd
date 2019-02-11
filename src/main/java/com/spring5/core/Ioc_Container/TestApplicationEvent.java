package com.spring5.core.Ioc_Container;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * spring事件机制
 */
public class TestApplicationEvent {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(TestApplicationEvent.class);
        context.register(MyEnventLister.class); //注册事件监听
        context.register(MyEventPublisher.class);//注册事件发布器
        context.refresh();

        MyEventPublisher publisher = context.getBean(MyEventPublisher.class);
        publisher.publishMyEvent(new MyEvent(1,"自定义事件"));

    }
    //时间监听器
    static final class MyEnventLister implements ApplicationListener<MyEvent> {
        @Override
        public void onApplicationEvent(MyEvent event) {
            System.out.println("监听到事件："+event);
        }
    }
 
    //时间发布器(目的是获取ApplicationEventPublisher的引用)
    static final class MyEventPublisher implements ApplicationEventPublisherAware{

        private ApplicationEventPublisher applicationEventPublisher;

        @Override
        public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
            this.applicationEventPublisher = applicationEventPublisher;
        }

        public void publishMyEvent(MyEvent myEvent){
            System.out.println("发布事件："+myEvent);
            applicationEventPublisher.publishEvent(myEvent);
        }
    }

    //自定义事件类
    static final class MyEvent extends ApplicationEvent{
        //事件信息
        private String message;

        public MyEvent(Object source,String message) {
            super(source);
            this.message = message;

        }

        @Override
        public String toString() {
            return "MyEvent{" +
                    "message='" + message + '\'' +
                    '}';
        }
    }
}
