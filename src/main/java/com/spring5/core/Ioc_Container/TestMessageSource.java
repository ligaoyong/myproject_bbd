package com.spring5.core.Ioc_Container;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ResourceBundleMessageSource;

import java.util.Locale;

/**
 * spring的国际化
 */
public class TestMessageSource {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(TestMessageSource.class);
        context.refresh();
        MessageSource messageSource = context;  //applicationContext实现了MessageSource接口

        //获取英语环境下的message
        String en_US_message = messageSource.getMessage("message", null, new Locale("en_US"));
        System.out.println("en_US message: "+en_US_message);
        //获取中文环境下的message
        String zh_CN_message = messageSource.getMessage("message", null, new Locale("zh_CN"));
        System.out.println("zh_CN message: "+zh_CN_message);

        //获取英语环境下的argument.required
        String en_US_message1 = messageSource.getMessage("argument.required", new Object[]{1}, new Locale("en_US"));
        System.out.println("en_US argument.required: "+en_US_message1);
        //获取中文环境下的argument.required
        String zh_CN_message1 = messageSource.getMessage("argument.required", new Object[]{1}, new Locale("zh_CN"));
        System.out.println("zh_CN argument.required: "+zh_CN_message1);
    }

    @Bean
    public MessageSource messageSource(){
        ResourceBundleMessageSource source = new ResourceBundleMessageSource();
        //注意此处设置的BaseName：只需要写文件名即可 不需要写后缀(.properties) 也不需要到地区
        source.setBasenames("format", "exceptions");//设置消息资源文件
        return source;
    }

}
