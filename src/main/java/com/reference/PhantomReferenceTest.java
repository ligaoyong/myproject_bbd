package com.reference;

import java.lang.ref.PhantomReference;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.util.ArrayList;
import java.util.List;

/**
 * 测试虚引用
 *      无法通过虚引用获取对象
 * @author ligaoyong@gogpay.cn
 * @date 2020/3/9 16:27
 */
@SuppressWarnings("all")
public class PhantomReferenceTest {

    static class Student{
        @Override
        protected void finalize() throws Throwable {
            System.out.println("我被回收了");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ReferenceQueue referenceQueue = new ReferenceQueue();
        //创建虚应用需要传入一个引用队列
        PhantomReference<Student> phantomReference = new PhantomReference<>(new Student(),referenceQueue);

        System.out.println(phantomReference.get()); // null 无法通过虚引用获取对象

        System.gc();
        //gc后一定会被回收 且将引用放到队列中
        System.out.println(referenceQueue.poll());

        Thread.sleep(3000);
    }
}
