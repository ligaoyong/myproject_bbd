package com.spring5.web.dispatcherServlet;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;

@Configuration
public class WebMvcConfig{

    @Bean
    public LocaleResolver localeResolver(){
        return new CookieLocaleResolver();
    }

}
