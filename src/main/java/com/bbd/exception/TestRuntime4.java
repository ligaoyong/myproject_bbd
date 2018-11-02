package com.bbd.exception;

/**
 * Created by wuyongchong on 2018/9/25.
 */
public class TestRuntime4 {
    public static void main(String[] args) {
        try {
            throw new IllegalArgumentException();
        } catch (Exception e) {
            if(e instanceof IllegalArgumentException){
                System.out.println("xxxx");
            }
        }
    }
}
