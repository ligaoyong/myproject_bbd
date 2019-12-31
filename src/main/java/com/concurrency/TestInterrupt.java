package com.concurrency;

/**
 * 测试中断
 */
@SuppressWarnings("all")
public class TestInterrupt {
    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(() -> {
            try {
                System.out.println("thread : "+Thread.currentThread().getState().name());
                System.out.println("开始睡觉！");
                Thread.sleep(10000);
                System.out.println("结束睡觉！");
            } catch (InterruptedException e) {
                e.printStackTrace();
                //该方法会清除中断标志位
                System.out.println(Thread.interrupted()); // false  为什么？ 只有wait或者time_wait状态下的中断会抛出异常 并且清除中断标志位
                /**
                 * 线程6大状态 ：NEW，RUNNABLE，BLOCKED，WAITING，TIMED_WAITING，TERMINATED（Thread类中有一个State枚举类型列举了线程的所有状态）
                 * NEW,TERMINATED:
                 *      对于处于new和terminated状态的线程对于中断是屏蔽的，也就是说中断操作对这两种状态下的线程是无效的
                 * RUNNABLE，BLOCKED：
                 *      这两种状态下的线程对于中断操作只是设置中断标志位并没有强制终止线程，对于线程的终止权利依然在程序手中；
                 *      我们可以检查线程的状态来做出相应的处理；如阻塞队列的阻塞操作、竞争锁对象产生阻塞等，在阻塞操作中我们
                 *      可以检查中断标志位来做出一些处理(用专业术语来说，这些操作可以响应中断)
                 * WAITING，TIMED_WAITING：
                 *      WAITING/TIMED_WAITING状态下的线程对于中断操作是敏感的，他们会抛出异常并清空中断标志位。
                 *      但是线程并不会退出，因为InterruptedException不是RuntimeException
                 *      我们的线程还可以继续往下进行操作，如下面的：System.out.println("发生中断操作后还可以进行操作");
                 */
            }
            System.out.println("发生中断操作后还可以进行操作");
        });
        thread.start();
        Thread.sleep(1000);
        // 该方法不会清楚中断标志位
        System.out.println(thread.isInterrupted());  //中断之前的标志：false
        thread.interrupt();//调用中断
        Thread.yield();
    }
}
