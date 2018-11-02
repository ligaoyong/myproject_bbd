package com.bbd.exception;

/**
 * Created by wuyongchong on 2018/9/26.
 */
public class TestRuntime5 {
    public static void main(String[] args) {
        try {
            throw new RuntimeException("ss");
        } catch (RuntimeException e) {
            System.out.println("xxxxx");
        }
    }
}
