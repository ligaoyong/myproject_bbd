package com.enumAndAnnotation.annotation;

/**
 * Created by lgy on 2018/11/16.
 * 测试类
 */
public class Sample {

    @MyTest public static void m1() { } // Test should pass

    public static void m2() { }

    @MyTest public static void m3() { // Test should fail
        throw new RuntimeException("Boom");
    }

    public static void m4() { }

    @MyTest public void m5() { } // INVALID USE: nonstatic method

    public static void m6() { }

    @MyTest public static void m7() { // Test should fail
        throw new RuntimeException("Crash");
    }
    public static void m8() { }

}
