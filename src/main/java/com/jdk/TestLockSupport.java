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

    /**
     * 测试线程 调用park后的状态  blocked 还是 waiting?
     *      答案：进入waiting状态 并且被中断后不会抛出异常 并正常往下执行
     */
    @Test
    public void test3() throws InterruptedException {
        Thread thread1 = new Thread(()->{
            try {
                Thread.sleep(2000);
                System.out.println("线程1调用park");
                LockSupport.park();
                System.out.println("线程1恢复执行,线程状态："+Thread.currentThread().getState()); //Runnable
            } catch (InterruptedException e) {
                e.printStackTrace();
                //没有执行 从在调用park后的线程被中断后 不会抛出异常 只是返回正常状态 这点与其他的waiting不同
                System.out.println("线程被中断，线程状态："+Thread.currentThread().getState());
            }
        });
        thread1.start();

        Thread.sleep(4000); //先睡3s，保证thread1已经调用了park 当前线程进入waiting状态
        System.out.println("thread1 调用 park 后的状态："+thread1.getState()); //waiting
        thread1.interrupt();//此时中断线程  线程会从waiting状态返回到Runnable状态正常执行 且不会抛出异常

        Thread.currentThread().join();
    }
}
