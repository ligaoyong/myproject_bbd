package com.threadPool;

import org.junit.Test;

public class ThreadAndRunable {
    public static void main(String[] args) throws InterruptedException {
        Runnable runnable = () -> {
            System.out.println("thread : " + Thread.currentThread().getName());
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };
        Thread thread = new Thread(runnable);
        thread.setName("aaaa");
        thread.start();
        thread.join(); //在当前线程(Main)中等待thread线程执行完成再执行
    }
}
