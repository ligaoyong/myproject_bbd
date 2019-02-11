package com.spring5.core.Ioc_Container;

import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.core.convert.support.GenericConversionService;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import javax.management.MXBean;
import java.io.IOException;

public class TestResourceLoader {
    public static void main(String[] args) throws IOException {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(TestResourceLoader.class);
        context.refresh();
        //Resource resource = context.getResource("file:D:\\py_test.txt");//需要加上前缀 否则不知道从哪里加载资源
        //System.out.println(resource.contentLength());

        TestResourceLoader bean = context.getBean(TestResourceLoader.class);
        ResourceLoader resourceLoader = bean.resourceLoader;
        Resource resource = resourceLoader.getResource("file:D:\\py_test.txt");
        System.out.println(resource.contentLength());

        ConversionService conversionService = context.getBean(ConversionService.class);
        String target = conversionService.convert(11, String.class);
        System.out.println(target.getClass());
    }

    @Autowired
    private ResourceLoader resourceLoader;

    @Autowired
    private BeanFactory beanFactory;

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    private ApplicationContext ctx;

    /**
     * 凡是需要实现xxxAware以获取到特殊Bean的引用的 无需实现接口 使用@Autowired直接注入即可
     */

    @Bean
    public ConversionService conversionService(){
        return new DefaultConversionService();
    }
}
