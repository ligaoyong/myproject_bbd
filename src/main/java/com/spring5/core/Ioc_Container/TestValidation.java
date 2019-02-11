package com.spring5.core.Ioc_Container;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.MapBindingResult;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.bind.EscapedErrors;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class TestValidation {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(TestValidation.class);
        context.refresh();

        /*Validator validator = context.getBean(Validator.class);
        Errors errors = new BeanPropertyBindingResult(null,null);
        validator.validate(new Person("aaaaaaaaaa",33),errors);*/

    }

    @Bean
    public Person person(){
        return new Person("aaaaaaaaaaaa", 33);
    }

    @Bean
    public Validator validator(){
        return new LocalValidatorFactoryBean();
    }

    static final class Person{
        @NotNull
        @Size(max = 5)  //使用javax包下的注解 spring能识别
        String name;

        @Min(20)
        @Max(30)
        Integer age;

        public Person(String name, Integer age) {
            this.name = name;
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getAge() {
            return age;
        }

        public void setAge(Integer age) {
            this.age = age;
        }
    }
}
