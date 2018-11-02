package com.bbd.exception;


/**
 * Created by wuyongchong on 2018/9/26.
 */
public class TestRuntime6{
    public static void main(String[] args) {
        try {
            throw new Exception(new RuntimeException(new IllegalArgumentException(new NullPointerException())));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
