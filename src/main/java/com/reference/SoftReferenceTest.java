package com.reference;

import java.lang.ref.SoftReference;

/**
 * 测试软引用
 *  内存不够时被回收：可以用来做缓存
 * @author ligaoyong@gogpay.cn
 * @date 2020/3/9 16:32
 */
public class SoftReferenceTest {
    static class Student{
        @Override
        protected void finalize() throws Throwable {
            System.out.println("我被回收了");
        }
    }

    public static void main(String[] args) {
        SoftReference<Student> softReference = new SoftReference<>(new Student());
        System.out.println(softReference.get());
        System.gc();
        //内存充足的时候不会被回收
        System.out.println(softReference.get());

        //使内存不足 启动时加上-Xmx20M
        byte[] bytes = new byte[1024 * 1024 * 13];
        //内存不足会被回收
        System.gc();
        System.out.println(softReference.get());
    }
}
