package com.spring5.core.Ioc_Container;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Description;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.jdbc.datasource.DelegatingDataSource;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

@Component
public class MyBeanFactoryPostProcessor implements BeanFactoryPostProcessor {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();

        context.scan("com.spring5");
        context.refresh();
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
        //可以获取到BeanDefinition
        BeanDefinition myBeanFactoryPostProcessor = configurableListableBeanFactory.getBeanDefinition("myBeanFactoryPostProcessor");
        System.out.println("beanDefinition 描述："+myBeanFactoryPostProcessor.toString());
        String[] beanDefinitionNames = configurableListableBeanFactory.getBeanDefinitionNames();
        Arrays.stream(beanDefinitionNames).forEach(System.out::println);
    }

    @Bean("test_str")
    //@Transactional("platformTransactionManager") //可以指定事务管理器 事务管理器包含了数据源信息
    public String str(){
        return "text";
    }

    @Bean("platformTransactionManager")
    @Description("事务管理器")
    public PlatformTransactionManager platformTransactionManager(){
        return new DataSourceTransactionManager(new DelegatingDataSource());
    }

    @Bean("platformTransactionManager1")
    public PlatformTransactionManager platformTransactionManager2(){
        return new DataSourceTransactionManager(new DelegatingDataSource());
    }
}
