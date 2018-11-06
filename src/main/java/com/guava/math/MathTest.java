package com.guava.math;

import com.google.common.math.BigIntegerMath;
import com.google.common.math.IntMath;
import com.google.common.math.LongMath;
import com.google.common.primitives.Ints;
import org.junit.Test;

import java.math.RoundingMode;

/**
 * Created by wuyongchong on 2018/11/6.
 * 数学运算工具
 */
public class MathTest {

    /**
     * 基本的独立数学函数根据所涉及的主要数字类型被划分为IntMath类、LongMath类、DoubleMath类和BigIntegerMath类。
     * 这些类具有并行结构，但每个类只支持函数的相关子集。
     * 请注意，在com.google.common. element类(比如int)中可能会找到一些不那么数学化的函数。
     */
    @Test public void test1(){
        int i = LongMath.log2(1024, RoundingMode.CEILING);
        System.out.println(i);
    }
}
