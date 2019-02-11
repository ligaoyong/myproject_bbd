package com.concurrency;

import sun.misc.ObjectInputFilter;

import java.util.concurrent.TimeUnit;

/**
 * Created by wuyongchong on 2018/11/26.
 * 测试一个线程修改变量对另一个线程的可见性
 */
public class StopThread {
    //volatile保证内存可见性
    //一个线程对共享变量的修改会被刷新到主存 另一个线程读取的时候也会从主存中读取
    //这就保证了一个线程的修改对另一个线程的可见性
    private volatile static boolean stopRequested;

    public static void main(String[] args) throws InterruptedException {
        Thread backgroundThread = new Thread(() -> {
            int i = 0;
            while (!stopRequested) {
                i++;
                System.out.println("i=" + i);
            }
        });
        backgroundThread.start();
        //TimeUnit.SECONDS.sleep(1);
        Thread.sleep(1000);
        stopRequested = true;
    }
}
