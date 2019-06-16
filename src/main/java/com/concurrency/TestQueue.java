package com.concurrency;

import org.junit.Test;

import java.util.PriorityQueue;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * 测试常用的队列和阻塞队列 ：Queue BlockQueue
 */
public class TestQueue {

    @Test
    public void test1(){
        //并发安全的传统队列：链表实现
        Queue queue = new ConcurrentLinkedQueue();
        //非线程安全的 带优先级的队列
        Queue queue1 = new PriorityQueue();
    }

    @Test
    public void test2(){
        //阻塞队列：队列满添加阻塞，知道队列不满  队列空获取阻塞，直到队列不空
        BlockingQueue blockingQueue = new ArrayBlockingQueue(10);
    }
}
