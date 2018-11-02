package com.bbd.exception;

/**
 * Created by wuyongchong on 2018/9/21.
 */
public class TestRuntime2 {
    public static void main(String[] args) {
        /*try {
            throw new RuntimeException("xxx");
        }catch (Exception e){
            System.out.println(e.getMessage());
        }*/
        try {
            method1();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void method1(){
        throw new RuntimeException("method1");
    }
}
