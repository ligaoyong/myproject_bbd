package com.concurrency;

import org.omg.PortableServer.THREAD_POLICY_ID;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.FutureTask;

public class TestFutrueTask {
    public static void main(String[] args) throws InterruptedException {
        FutureTask<String> futureTask = new FutureTask<>(()->{
            Thread.sleep(3000);
            return "ok";
        });
        CompletableFuture<Void> runAsync = CompletableFuture.runAsync(futureTask); //异步执行
        //futureTask.run();// 同步执行 在main线程中执行
        System.out.println("继续执行");
        Thread.currentThread().join();
    }
}
