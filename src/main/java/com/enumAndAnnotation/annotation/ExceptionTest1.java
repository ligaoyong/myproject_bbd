package com.enumAndAnnotation.annotation;

/**
 * Created by wuyongchong on 2018/11/16.
 * 使用注解ExceptionTest
 */
public class ExceptionTest1 {

    @ExceptionTest(value = {ArithmeticException.class,IllegalArgumentException.class})  //pass
    public void m1(){
        int i = 1 / 0;
    }

    @ExceptionTest(value = {NullPointerException.class})    //fail
    public void m2(){
        throw new IllegalArgumentException();
    }

    public void m3(){}  //no run
}
