package com.reference;

import java.lang.ref.WeakReference;

/**
 * 测试弱引用
 *  gc后就被回收
 *      弱引用在很多地方都有用到，比如ThreadLocal、WeakHashMap。
 * @author ligaoyong@gogpay.cn
 * @date 2020/3/9 16:27
 */
@SuppressWarnings("all")
public class WeakReferenceTest {

    static class Student{
        @Override
        protected void finalize() throws Throwable {
            System.out.println("我被回收了");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        WeakReference<Student> weakReference = new WeakReference<>(new Student());
        System.out.println(weakReference.get());
        System.gc();
        System.out.println(weakReference.get()); // null
    }
}
