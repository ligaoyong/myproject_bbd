package com.method;

import com.google.common.base.Preconditions;
import org.junit.Assert;
import org.junit.Test;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by wuyongchong on 2018/11/19.
 * 方法参数
 */
public class Params {

    //校验参数的有效性
    @Test public void test1(){
        //1、非空校验
        //Objects.requireNonNull(null, "参数不能为空！！");
        try {
            assert 1 < 0; // java.lang.AssertionError 不是异常 是错误
        } catch (Error e) { //捕获的时候需要声明为Error或者Throwable
            e.printStackTrace();
        }
    }

    @Test public void test2(){
        //基本类型的Optional
        OptionalInt optionalInt = OptionalInt.of(10);
        System.out.println(optionalInt.orElse(101));

        OptionalDouble.empty();
        OptionalLong.empty();

        //随机数生成
        int i = ThreadLocalRandom.current().nextInt(1, 200);
        System.out.println(i);
    }

}
