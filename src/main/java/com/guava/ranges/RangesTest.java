package com.guava.ranges;

import com.google.common.collect.BoundType;
import com.google.common.collect.Range;
import com.google.common.primitives.Ints;
import org.junit.Test;

/**
 * Created by wuyongchong on 2018/11/5.
 * 范围工具Ranges
 */
public class RangesTest {
    /**
     * 静态方法创建
     * Range type	    Method
        (a..b)	        open(C, C)
        [a..b]	        closed(C, C)
        [a..b)	        closedOpen(C, C)
        (a..b]	        openClosed(C, C)
        (a..+∞)	        greaterThan(C)
        [a..+∞)	        atLeast(C)
        (-∞..b)	        lessThan(C)
        (-∞..b]	        atMost(C)
        (-∞..+∞)	    all()
     */
    @Test public void test1(){
        Range<Integer> range = Range.openClosed(1, 4);
        System.out.println(range);
        System.out.println(range.containsAll(Ints.asList(2,3)));
    }

    /**
     * 显示传参创建
     * range(C, BoundType, C, BoundType) BoundType---》Closed/open
     * downTo(C, BoundType)
     * upTo(C, BoundType)
     */
    @Test public void test2(){
        Range<Integer> range = Range.range(1, BoundType.CLOSED, 4, BoundType.OPEN);
        System.out.println(range);
        System.out.println(range.contains(2));
    }

    /**
     * 常用操作
     */
    @Test public void test3(){
        //1、intersection    交集
        System.out.println(Range.closed(1,5).intersection(Range.closed(2,6)));

        //2、span 并集
        System.out.println(Range.closed(1,5).span(Range.closed(2,10)));
    }
}
