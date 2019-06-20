package com.async;

import org.junit.Test;

import java.net.ServerSocket;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * 测试CompletableFeture类
 */
public class CompletableFeture {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        //CompletableFuture比较常规的使用方式 用lambda表达式进行异步计算
        CompletableFuture<Integer> integerCompletableFuture = CompletableFuture.supplyAsync(() -> {
            System.out.println("异步任务开始");
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("异步任务结束");
            return 1;
        });
        System.out.println("主线程获取结果：");
        System.out.println(integerCompletableFuture.get());
    }

    @Test
    public void test1() throws ExecutionException, InterruptedException {
        CompletableFuture<Object> objectCompletableFuture = new CompletableFuture<>();
        Runnable runnable = () -> {
            System.out.println("异步任务开始");
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("异步任务结束");
            objectCompletableFuture.complete(1); //在异步线程中设置值返回
        };
        Thread thread = new Thread(runnable);
        thread.start();
        System.out.println("主线程获取结果：");
        System.out.println(objectCompletableFuture.get());
    }

    @Test
    public void test2() throws ExecutionException, InterruptedException {
        CompletableFuture<Integer> integerCompletableFuture = CompletableFuture.supplyAsync(() -> {
            System.out.println("异步任务开始");
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("异步任务结束");
            return 1;
        });
        //将第一个异步的结果作为参数传递给第二个异步计算
        CompletableFuture<String> stringCompletableFuture =
                integerCompletableFuture.thenApplyAsync((x) -> {
                    System.out.println("开启第二个异步计算");
                    x++;
                    System.out.println("第二个异步计算结束，返回计算结果");
                    return x.toString();
                });
        System.out.println("获取最终异步计算的结果：");
        System.out.println(stringCompletableFuture.get());
    }
}
