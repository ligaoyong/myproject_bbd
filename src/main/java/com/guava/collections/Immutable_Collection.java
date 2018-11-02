package com.guava.collections;

import com.google.common.collect.ImmutableSet;
import org.junit.Test;

/**
 * Created by wuyongchong on 2018/11/2.
 * 不可变集合
 */
public class Immutable_Collection {
    @Test
    public void test1(){
        ImmutableSet<Integer> of = ImmutableSet.of(1, 2);
    }
}
