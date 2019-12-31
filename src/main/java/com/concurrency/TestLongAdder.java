package com.concurrency;

import java.util.concurrent.atomic.LongAdder;

/**
 * 解决线程数量多、竞争激烈情况下cas大量失败重试（自旋）的机制
 *
 * 加算器
 * @author ligaoyong@gogpay.cn
 * @date 2019/12/31 15:30
 */
public class TestLongAdder {

    public static void main(String[] args) {
        LongAdder longAdder = new LongAdder();
        //见TestCAS的测试结果
    }
}
