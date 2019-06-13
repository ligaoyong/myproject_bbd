package com.threadPool;

import org.junit.Test;

public class ThreadAndRunable {
    public static void main(String[] args) {
        Runnable runnable = ()-> System.out.println("thread : "+Thread.currentThread().getName());
        Thread thread = new Thread(runnable);
        thread.setName("aaaa");
        thread.run();
    }
}
