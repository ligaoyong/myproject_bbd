package com.redisson;

import org.junit.Test;
import org.redisson.Redisson;
import org.redisson.api.RAtomicLong;
import org.redisson.api.RFuture;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * RedissonClient 的简单调用方式
 * Redisson框架提供的几乎所有对象都包含了同步和异步相互匹配的方法
 */
public class Example1 {

    public static void main(String[] args) {
        RedissonClient redissonClient = RedissonConfig.newSingleInstance();
        //计数器
        RAtomicLong myLong = redissonClient.getAtomicLong("myLong");
        myLong.set(1);
        System.out.println(myLong.get());
    }

    /**
     * 调用方式：同步和异步调用
     */
    @Test
    public void test1() throws InterruptedException, ExecutionException, TimeoutException {
        RedissonClient redissonClient = RedissonConfig.newSingleInstance();
        RAtomicLong myLong = redissonClient.getAtomicLong("myLong");
        //同步
        myLong.compareAndSet(1, 400);
        System.out.println(myLong);
        //异步
        RFuture<Boolean> booleanRFuture = myLong.compareAndSetAsync(400, 800);
        System.out.println(myLong);
        System.out.println(booleanRFuture.getNow());
        System.out.println(booleanRFuture.get(5, TimeUnit.SECONDS));
    }

}
