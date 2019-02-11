package com.spring5.core.Ioc_Container;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableLoadTimeWeaving;
import org.springframework.context.weaving.LoadTimeWeaverAware;
import org.springframework.instrument.classloading.LoadTimeWeaver;

@Configuration
@EnableLoadTimeWeaving
public class TestLoadTimeWeaver implements LoadTimeWeaverAware {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(TestLoadTimeWeaver.class);
        context.refresh();
    }

    @Override
    public void setLoadTimeWeaver(LoadTimeWeaver loadTimeWeaver) {
        ClassLoader instrumentableClassLoader = loadTimeWeaver.getInstrumentableClassLoader();
        //ClassLoader throwawayClassLoader = loadTimeWeaver.getThrowawayClassLoader();
    }
}
