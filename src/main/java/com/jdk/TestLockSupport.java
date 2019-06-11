package com.jdk;

import org.junit.Test;

import java.util.concurrent.locks.LockSupport;

/**
 * 测试LockSupport 阻塞一个线程 比较底层的方式
 */
public class TestLockSupport {
    /**
     * 先park 再unpark
     * @throws InterruptedException
     */
    @Test
    public void test1() throws InterruptedException {
        Thread thread1 = new Thread(()->{
            System.out.println("线程1调用park阻塞");
            LockSupport.park();
            System.out.println("线程1恢复执行");
        });
        thread1.start();
        Thread.sleep(2000);

        System.out.println("主线程调用unpark唤醒线程1");
        LockSupport.unpark(thread1); //唤醒线程1
    }

    /**
     * 先unpark 再park  不会阻塞
     * @throws InterruptedException
     */
    @Test
    public void test2() throws InterruptedException {
        Thread thread1 = new Thread(()->{
            try {
                Thread.sleep(2000);
                System.out.println("线程1调用park");
                LockSupport.park(); //此时就不会阻塞了 因为之前已经调用unpark获取许可证了
                System.out.println("线程1恢复执行");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        thread1.start();

        System.out.println("主线程先调用unpark");
        LockSupport.unpark(thread1); //唤醒线程1
        Thread.currentThread().join();
    }
}
