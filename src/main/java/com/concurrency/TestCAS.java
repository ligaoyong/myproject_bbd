package com.concurrency;

import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 测试cas操作
 *
 * @author ligaoyong@gogpay.cn
 * @date 2019/12/31 10:18
 */
@SuppressWarnings("all")
public class TestCAS {

    private volatile Integer count = 0;

    /**
     * 默认情况下，只能被由BootstrapClassLoader加载器加载的类所使用
     * 1、通过破坏类加载机制来获取
     * 2、通过反射来获取，这里通过第二种方式
     *
     * @return
     */
    private Unsafe getUnsafe() throws NoSuchFieldException, IllegalAccessException {
        Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
        theUnsafe.setAccessible(true);
        return (Unsafe) theUnsafe.get(null);
    }

    /**
     * 通过cas更新
     *
     * @param object
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     */
    private void updateByCas(TestCAS object) throws NoSuchFieldException, IllegalAccessException, InterruptedException {
        Unsafe unsafe = object.getUnsafe();
        //获取属性的偏移量
        long offset = unsafe.objectFieldOffset(TestCAS.class.getDeclaredField("count"));
        //用10个线程进行测试
        ExecutorService threadPool = Executors.newFixedThreadPool(10);
        CountDownLatch countDownLatch = new CountDownLatch(10);
        Runnable runnable = () -> {
            for (int i = 0; i < 10000000; i++) {
//                Integer count = object.count;
//                //System.out.println("threa1一系列的操作");
//                //cas 第一个参数是对象  第二个参数是属性的偏移量 第三个参数是对比值 第四个参数是期望值
//                //偏移量上的值和对比值一直 就更新为期望值
//                boolean b = unsafe.compareAndSwapObject(object, offset, count, count + 1);
//                if (b){ //更新成功 才算一次 为了更新足10000次
//                    i++;
//                }
                for (; ; ) {
                    Integer count = object.count;
                    if (unsafe.compareAndSwapObject(object, offset, count, count + 1)) {
                        break;
                    }
                }
            }
            countDownLatch.countDown();
        };
        long start = System.currentTimeMillis();
        for (int i = 0; i < 10; i++) {
            threadPool.execute(runnable);
        }
        countDownLatch.await();
        System.out.println("updateByCas -- count : " + object.count);
        System.out.println("updateByCas -- 花费时间 ： " + String.valueOf(System.currentTimeMillis() - start));
        threadPool.shutdownNow();
    }

    /**
     * 通过lock更新
     *
     * @param object
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     * @throws InterruptedException
     */
    private void updateByLock(TestCAS object) throws NoSuchFieldException, IllegalAccessException, InterruptedException {
        ReentrantLock reentrantLock = new ReentrantLock();
        ExecutorService threadPool = Executors.newFixedThreadPool(10);
        CountDownLatch countDownLatch = new CountDownLatch(10);
        Runnable runnable = () -> {
            for (int i = 0; i < 10000000; i++) {
                reentrantLock.lock();
                Integer count = object.count;
                object.count = count + 1;
                reentrantLock.unlock();
            }
            countDownLatch.countDown();
        };
        long start = System.currentTimeMillis();
        for (int i = 0; i < 10; i++) {
            threadPool.execute(runnable);
        }
        countDownLatch.await();
        System.out.println("updateByLock -- count : " + object.count);
        System.out.println("updateByLock -- 花费时间 ： " + String.valueOf(System.currentTimeMillis() - start));
        threadPool.shutdownNow();
    }

    /**
     * 通过Synchronized更新
     *
     * @param object
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     * @throws InterruptedException
     */
    private void updateBySynchronized(TestCAS object) throws NoSuchFieldException, IllegalAccessException, InterruptedException {
        ExecutorService threadPool = Executors.newFixedThreadPool(10);
        CountDownLatch countDownLatch = new CountDownLatch(10);
        Runnable runnable = () -> {
            for (int i = 0; i < 10000000; i++) {
                synchronized (object) {
                    Integer count = object.count;
                    object.count = count + 1;
                }
            }
            countDownLatch.countDown();
        };
        long start = System.currentTimeMillis();
        for (int i = 0; i < 10; i++) {
            threadPool.execute(runnable);
        }
        countDownLatch.await();
        System.out.println("updateBySynchronized -- count : " + object.count);
        System.out.println("updateBySynchronized -- 花费时间 ： " + String.valueOf(System.currentTimeMillis() - start));
        threadPool.shutdownNow();
    }

    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException, InterruptedException {
        TestCAS object = new TestCAS();
        object.updateByCas(object);
        object.updateByLock(object);
        object.updateBySynchronized(object);

        /**
         * 结果测试
         *      2个线程的情况下 进行1000万次更新 cas 676时间戳 lock 1015个时间戳 cas大概是0.7倍的lock 差距并不是那么大
         *      10个线程的情况下 进行1000万次更新 cas 8906时间戳 lock 3450个时间戳 syn 4220个时间戳 cas远不如lock和syn
         */

        /**
         * 总结及原因
         *      在线程数量较少 竞争不激烈的情况下  cas有一定的优势
         *      但在线程数量较多 竞争非常激烈是 cas性能会大大降低  反而不如lock和synchronized的 而且cpu会飙升到100%
         *      这是因为竞争激烈的情况下  cas会出现大量的失败重试次数，反而不如锁的性能
         */
    }
}
