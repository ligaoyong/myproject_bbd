package com.enumAndAnnotation.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by lgy on 2018/11/16.
 * 异常测试类，如果方法抛出改注释指定的异常类型，则测试通过
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ExceptionTest {

    Class<? extends Throwable>[] value();

}
