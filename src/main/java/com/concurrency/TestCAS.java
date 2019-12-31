package com.concurrency;

import sun.misc.Unsafe;

import java.lang.reflect.Field;
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
     * @return
     */
    private Unsafe getUnsafe() throws NoSuchFieldException, IllegalAccessException {
        Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
        theUnsafe.setAccessible(true);
        return (Unsafe) theUnsafe.get(null);
    }

    /**
     * 通过cas更新
     * @param object
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     */
    private void updateByCas(TestCAS object) throws NoSuchFieldException, IllegalAccessException, InterruptedException {
        Unsafe unsafe = object.getUnsafe();
        //获取属性的偏移量
        long offset = unsafe.objectFieldOffset(TestCAS.class.getDeclaredField("count"));
        long start = System.currentTimeMillis();
        Thread thread1 = new Thread(()->{
            for (int i=0;i<10000000;){
                Integer count = object.count;
                //System.out.println("threa1一系列的操作");
                //cas 第一个参数是对象  第二个参数是属性的偏移量 第三个参数是对比值 第四个参数是期望值
                //偏移量上的值和对比值一直 就更新为期望值
                boolean b = unsafe.compareAndSwapObject(object, offset, count, count + 1);
                if (b){ //更新成功 才算一次 为了更新足10000次
                    i++;
                }
            }
        });

        Thread thread2 = new Thread(()->{
            for (int i=0;i<10000000;){
                Integer count = object.count;
                //System.out.println("threa2一系列的操作");
                //cas 第一个参数是对象  第二个参数是属性的偏移量 第三个参数是对比值 第四个参数是期望值
                //偏移量上的值和对比值一直 就更新为期望值
                boolean b = unsafe.compareAndSwapObject(object, offset, count, count + 1);
                if (b){//更新成功 才算一次 为了更新足10000次
                    i++;
                }
            }
        });

        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();

        System.out.println("updateByCas -- count : "+ object.count);
        System.out.println("updateByCas -- 花费时间 ： " + String.valueOf(System.currentTimeMillis() - start));
    }

    private void updateByLock(TestCAS object) throws NoSuchFieldException, IllegalAccessException, InterruptedException {
        ReentrantLock reentrantLock = new ReentrantLock();
        long start = System.currentTimeMillis();
        Thread thread1 = new Thread(()->{
            for (int i=0;i<10000000;i++){
                reentrantLock.lock();
                Integer count = object.count;
                object.count = count + 1;
                reentrantLock.unlock();
            }
        });

        Thread thread2 = new Thread(()->{
            for (int i=0;i<10000000;i++){
                reentrantLock.lock();
                Integer count = object.count;
                object.count = count + 1;
                reentrantLock.unlock();
            }
        });

        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();

        System.out.println("updateByLock -- count : "+ object.count);
        System.out.println("updateByLock -- 花费时间 ： " + String.valueOf(System.currentTimeMillis() - start));
    }

    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException, InterruptedException {
        TestCAS object = new TestCAS();
        object.updateByCas(object); // 676时间戳
        //object.updateByLock(object);//1015个时间戳
        //结果测试 在两个线程同步更新的情况下 进行1000万次更新 cas大概是0.7倍的lock 差距并不是那么大
    }
}
