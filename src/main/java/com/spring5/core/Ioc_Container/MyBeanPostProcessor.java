package com.spring5.core.Ioc_Container;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

/**
 * 执行顺序：
 *  设置完Bean的字段 ---> postProcessBeforeInitialization ---> 初始化方法(init) ---> postProcessAfterInitialization
 */
@Component
public class MyBeanPostProcessor implements BeanPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        System.out.println(beanName+" postProcessBeforeInitialization called........");
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        System.out.println(beanName+" postProcessAfterInitialization called........");
        return bean;
    }
}
