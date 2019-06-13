package com.threadPool;

import org.junit.Test;

import java.util.concurrent.*;

/**
 * 线程池的创建方式
 */
public class Create {
    @Test
    public void test1(){
        //通过Executors来创建 (不推荐 不可以调解参数)
        Executors.newFixedThreadPool(5);
    }

    @Test
    public void test2(){
        //推荐这种方式 可以控制参数
        ThreadPoolExecutor threadPool = new ThreadPoolExecutor(5, 10, // 核心线程数 、最大线程数
                0L, TimeUnit.SECONDS, //核心线程外的线程的最大空闲时间，超过时间销毁线程
                new LinkedTransferQueue<>(), Thread::new, // 任务队列
                new ThreadPoolExecutor.AbortPolicy());// 任务队列满的拒接策略
//        threadPool.submit()
    }

    /**
     * FutureTask
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @Test
    public void test3() throws ExecutionException, InterruptedException {
        FutureTask<Integer> futureTask = new FutureTask<>(() -> {
            Thread.sleep(5000);
            return 1;
        });
        futureTask.run();
        Integer integer = futureTask.get();
        System.out.println(integer);
    }
}
