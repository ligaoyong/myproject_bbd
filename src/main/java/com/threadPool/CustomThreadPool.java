package com.threadPool;

import java.util.Random;
import java.util.concurrent.*;

/**
 * 自定义线程池
 *     在任务执行前后进行一些操作
 */
public class CustomThreadPool extends ThreadPoolExecutor {
    /**
     * 构造器
     * @param corePoolSize
     * @param maximumPoolSize
     * @param keepAliveTime
     * @param unit
     * @param workQueue
     * @param threadFactory
     */
    public CustomThreadPool(int corePoolSize, int maximumPoolSize,
                            long keepAliveTime, TimeUnit unit,
                            BlockingQueue<Runnable> workQueue,
                            ThreadFactory threadFactory) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory);
    }

    /**
     * 任务执行前调用
     * @param t
     * @param r
     */
    @Override
    protected void beforeExecute(Thread t, Runnable r) {
        System.out.println("before execute thread : "+t.getName());
    }

    /**
     * 任务执行后调用
     * @param r
     * @param t
     */
    @Override
    protected void afterExecute(Runnable r, Throwable t) {
        String msg = t == null ? "" : " : " + t.getCause();
        System.out.println("after execute " + msg);

    }

    public static void main(String[] args) throws InterruptedException {
        int corePoolSize = 2;
        int maximumPoolSize = 3;
        int keepAliveTime = 1;
        TimeUnit timeUnit = TimeUnit.SECONDS;
        BlockingQueue<Runnable> blockingQueue = new ArrayBlockingQueue<>(1);
        ThreadFactory threadFactory = Executors.defaultThreadFactory();

        CustomThreadPool customThreadPool = new CustomThreadPool(corePoolSize, maximumPoolSize,
                keepAliveTime, timeUnit, blockingQueue, threadFactory);

        Runnable runnable = () -> {
            try {
                System.out.println("working ...");
                Thread.sleep(2000);
                if (new Random().nextBoolean()){
                    throw new RuntimeException("线程在工作中异常！");
                }
                System.out.println("worked!");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };

        customThreadPool.execute(runnable);
        customThreadPool.execute(runnable);
        customThreadPool.execute(runnable);
        customThreadPool.execute(runnable);

        /**
         * 自己等待自己完成 陷入阻塞中
         */
        Thread.currentThread().join();
    }
}
