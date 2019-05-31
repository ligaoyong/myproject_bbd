package com.redisson;

import org.junit.Before;
import org.junit.Test;
import org.redisson.RedissonMultiLock;
import org.redisson.RedissonRedLock;
import org.redisson.api.*;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 分布式锁
 */
public class DistributeLock {
    private static RedissonClient redisson = null;

    @Before
    public void before(){
        redisson = RedissonConfig.newSingleInstance();
    }

    /**
     * 信号量(Semaphore)  会话结束后不会自动删除
     * 闭锁（CountDownLatch）   会话结束后不会自动删除
     */
    @Test
    public void test6() throws InterruptedException {
        RSemaphore semaphore = redisson.getSemaphore("semaphore");
        boolean setPermits = semaphore.trySetPermits(2);
        System.out.println("setPermit : "+setPermits);
        ExecutorService threadPool = Executors.newFixedThreadPool(3);

        RCountDownLatch countDownLatch = redisson.getCountDownLatch("countDownLatch");
        countDownLatch.trySetCount(3);

        threadPool.execute(()->{
            try {
                semaphore.acquire(1);
                System.out.println("进程1 ：获取信号成功，处理业务");
                semaphore.release(1);
                System.out.println("进程1 ：释放信号量");

                countDownLatch.countDown();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        threadPool.execute(()->{
            try {
                semaphore.acquire(1);
                System.out.println("进程2 ：获取信号成功，处理业务");
                semaphore.release(1);
                System.out.println("进程2 ：释放信号量");

                countDownLatch.countDown();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        threadPool.execute(()->{
            try {
                semaphore.acquire(1);
                System.out.println("进程3 ：获取信号成功，处理业务");
                semaphore.release(1);
                System.out.println("进程3 ：释放信号量");

                countDownLatch.countDown();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        countDownLatch.await();
        semaphore.delete(); //semaphore 不会自动删除
        Thread.currentThread().join();
    }

    /**
     * 读写锁
     *      分布式可重入读写锁允许同时有多个读锁和一个写锁处于加锁状态。
     */
    @Test
    public void test5(){
        RReadWriteLock readWriteLock = redisson.getReadWriteLock("readWriteLock");
        RLock readLock = readWriteLock.readLock(); //读锁
        RLock writeLock = readWriteLock.writeLock(); //写锁

        readLock.lock();
        System.out.println("readLock locked");
        readLock.lock();
        System.out.println("readLock locked");
        readLock.lock();
        System.out.println("readLock locked");
        readLock.unlock();
        System.out.println("readLock unlocked");
        readLock.unlock();
        System.out.println("readLock unlocked");
        readLock.unlock();
        System.out.println("readLock unlocked");
        writeLock.lock();
        System.out.println("writeLock locked");
        writeLock.unlock();
        System.out.println("writeLock unlocked");
    }

    /**
     * 红锁
     *      在大部分节点上加锁成功就算成功
     */
    @Test
    public void test4(){
        RLock lock1 = redisson.getLock("lock1");
        RLock lock2 = redisson.getLock("lock2");
        RLock lock3 = redisson.getLock("lock3");

        RedissonRedLock redLock = new RedissonRedLock(lock1, lock2, lock3);

        lock1.lock();
        System.out.println("lock1加锁成功");
        redLock.lock();
        System.out.println("redLock加锁成功");
        redLock.unlock();
        System.out.println("redLock unLocked");
        lock1.unlock();
        System.out.println("lock1 unlocked");
    }

    /**
     * 联锁（MultiLock）
     *      基于Redis的Redisson分布式联锁RedissonMultiLock对象可以将多个RLock对象关联为一个联锁，
     *      每个RLock对象实例可以来自于不同的Redisson实例。
     */
    @Test
    public void test3() throws InterruptedException {
        RLock lock1 = redisson.getLock("lock1");
        RLock lock2 = redisson.getLock("lock2");
        RLock lock3 = redisson.getLock("lock3");

        RedissonMultiLock multiLock = new RedissonMultiLock(lock1, lock2, lock3);
        // 为加锁等待100秒时间，并在加锁成功10秒钟后自动解开
        multiLock.tryLock(100,10,TimeUnit.SECONDS);
        Thread.sleep(20000);
        multiLock.unlock();
    }

    /**
     * 公平锁（Fair Lock）
     *      它保证了当多个Redisson客户端线程同时请求加锁时，优先分配给先发出请求的线程。
     *      所有请求线程会在一个队列中排队，当某个线程出现宕机时，Redisson会等待5秒后继续下一个线程，
     *      也就是说如果前面有5个线程都处于等待状态，那么后面的线程会等待至少25秒。
     */
    @Test
    public void test2() throws InterruptedException {
        RLock fairLock = redisson.getFairLock("fairLock");
        ExecutorService threadPool = Executors.newFixedThreadPool(3);
        //10秒后自动释放锁
        fairLock.lock(10, TimeUnit.SECONDS);

        threadPool.execute(()->{
            try {
                Thread.sleep(10000);
                System.out.println("线程1 ：开始获取锁....");
                //获取锁 最多等待10s 获取后10s自动释放
                fairLock.tryLock(10, 10, TimeUnit.SECONDS);
                System.out.println("线程1 ：获取锁成功");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        threadPool.execute(()->{
            try {
                Thread.sleep(12000);
                System.out.println("线程2 ：开始获取锁....");
                //获取锁 最多等待22s 获取后10s自动释放
                fairLock.tryLock(22, 10, TimeUnit.SECONDS);
                System.out.println("线程2 ：获取锁成功");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        threadPool.execute(()->{
            try {
                Thread.sleep(14000);
                System.out.println("线程3 ：开始获取锁....");
                //获取锁 最多等待34s 获取后10s自动释放
                fairLock.tryLock(34, 10, TimeUnit.SECONDS);
                System.out.println("线程3 ：获取锁成功");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        Thread.currentThread().join();
    }

    /**
     * 可重入锁（Reentrant Lock）
     *      redis底层实现：使用hash来实现 key：一个字符串 value ：1
     *      当没有进程持有锁时 redis中没有这个锁对象
     */
    @Test
    public void test1() throws InterruptedException {
        /**
         * 如果负责储存这个分布式锁的Redisson节点宕机以后，而且这个锁正好处于锁住的状态时，这个锁会出现锁死的状态。
         * 为了避免这种情况的发生，Redisson内部提供了一个监控锁的看门狗，它的作用是在Redisson实例被关闭前，不断的延长锁的有效期。
         * 默认情况下，看门狗的检查锁的超时时间是30秒钟，也可以通过修改Config.lockWatchdogTimeout来另行指定
         *
         * 另外Redisson还通过加锁的方法提供了leaseTime的参数来指定加锁的时间。超过这个时间后锁便自动解开了。
         */
        ExecutorService threadPool = Executors.newFixedThreadPool(2);
        RLock lock = redisson.getLock("lock");

        // 加锁以后10秒钟自动解锁
        // 无需调用unlock方法手动解锁
        lock.lock(10, TimeUnit.SECONDS);
        Thread.sleep(20000);

        threadPool.execute(()->{
            try {
                boolean tryLock = lock.tryLock(20, 10, TimeUnit.SECONDS);
                if (tryLock){
                    System.out.println("进程1 ：获取锁成功！开始处理业务,10 s 后自动释放锁");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        threadPool.execute(()->{
            lock.lock();
            try {
                System.out.println("进程2 : 获取所成功！开始处理业务");
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
                System.out.println("进程2 ：处理完毕，释放锁");
            }
        });

        Thread.currentThread().join();
    }
}
