package com.concurrency;

import org.omg.PortableServer.THREAD_POLICY_ID;

import java.util.concurrent.*;

public class TestFutrueTask {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        //ExecutorService executorService = Executors.newFixedThreadPool(1);
        FutureTask<String> futureTask = new FutureTask<>(()->{
            Thread.sleep(30000);
            return "ok";
        });

        new Thread(futureTask).start();//异步执行
        //futureTask.run();// 同步执行 在main线程中执行
        System.out.println("继续执行");
        System.out.println("任务结果："+futureTask.get()); //阻塞
        Thread.currentThread().join();
    }
}
