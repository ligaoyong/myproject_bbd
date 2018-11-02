package com.guava.Basic_Utilities;

import com.google.common.base.Preconditions;
import org.junit.Test;

import java.util.Objects;

/**
 * Created by wuyongchong on 2018/11/2.
 * 先决条件
 */
public class TestPreconditions {
    /**
     * guava提供了一些先决条件检查实用程序。我们强烈建议静态导入这些。
     */

    //检查参数的值checkArgument() 抛出IllegalArgumentException
    @Test
    public void test1(){
        Integer i = 0;
        Integer j = 5;
        //Preconditions.checkArgument(i >= j, "Argument was %s but expected nonnegative", i);
        //占位符用%s
        Preconditions.checkArgument(i>j,"错误的表达式:%s > %s",i,j);
    }

    //检查非空checkNotNull(value)   抛出NullPointerException
    @Test
    public void test2(){
        Integer i = null;
        Preconditions.checkNotNull(i, "值为空：%s", i);
    }

    //检查状态checkState()  抛出IllegalStateException
    @Test
    public void test3(){
        Preconditions.checkState(1>2,"表达式错误：%s > %s",1,2);
    }


}
