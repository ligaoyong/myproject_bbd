package com.springboot.TestImportBeanDefinitionRegistrar;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(MyBeanDefinitionRegistrar.class)
public class MyConfig {
}
