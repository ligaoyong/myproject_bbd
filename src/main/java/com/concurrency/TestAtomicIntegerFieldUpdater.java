package com.concurrency;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

/**
 * 测试AtomicIntegerFieldUpdater
 *
 * @author ligaoyong@gogpay.cn
 * @date 2019/12/31 10:09
 */
@SuppressWarnings("all")
public class TestAtomicIntegerFieldUpdater {

    private volatile int count = 0;

    private static final AtomicIntegerFieldUpdater updater =
            AtomicIntegerFieldUpdater.newUpdater(TestAtomicIntegerFieldUpdater.class, "count");

    @SuppressWarnings("all")
    public static void main(String[] args) throws InterruptedException {
        TestAtomicIntegerFieldUpdater object = new TestAtomicIntegerFieldUpdater();
        Thread thread1 = new Thread(() -> {
            for (int i = 0; i < 10000; i++) {
                updater.addAndGet(object, 1);
            }
        });

        Thread thread2 = new Thread(() -> {
            for (int i = 0; i < 10000; i++) {
                updater.addAndGet(object, 1);
            }
        });

        thread1.start();
        thread2.start();

        //当前线程等待thread1完成
        thread1.join();
        thread2.join();

        System.out.println("count : "+ String.valueOf(object.count));
    }
}
