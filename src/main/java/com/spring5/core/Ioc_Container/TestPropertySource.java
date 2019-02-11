package com.spring5.core.Ioc_Container;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertySource;

public class TestPropertySource {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        //context.scan("com.spring5");
        context.register(TestPropertySource.class);//手动构建容器
        context.refresh();
        MutablePropertySources propertySources = context.getEnvironment().getPropertySources();
        propertySources.addFirst(new MyPropertySource("my-property"));
        System.out.println("PropertySources:");
        propertySources.forEach(System.out::println);
        System.out.println("is contain my-property:"+propertySources.get("my-property").getName());
    }

    @Bean
    public String myBean(){
        return "myBean";
    }

    static final class MyPropertySource extends PropertySource{
        private MyPropertySource(String name) {
            super(name);
        }

        @Override
        public Object getProperty(String name) {
            return name;
        }
    }
}
