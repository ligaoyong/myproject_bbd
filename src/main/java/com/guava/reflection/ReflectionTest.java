package com.guava.reflection;

import com.google.common.reflect.TypeToken;
import org.junit.Test;

/**
 * Created by wuyongchong on 2018/11/6.
 * 反射工具包
 */
public class ReflectionTest {

    /**
     * Guava提供了TypeToken，它使用基于反射的技巧来允许操作和查询泛型类型，甚至在运行时也是如此
     */

    /**
     * 背景：类型擦除与反射
     * Java不会在运行时为对象保留泛型类型信息。但是，反射允许您检测方法和类的泛型.
     * 如果您实现了一个返回List<String>的方法，并且使用反射来获得该方法的返回类型，
     * 那么您将返回一个表示List<String>的参数化类型(ParameterizedType)
     * TypeToken类使用这种变通方法，允许使用最少的语法开销操作泛型类型。
     */

    @Test public void test1(){
        //获取一个基本的原始类的TypeToken非常简单
        TypeToken<String> stringTypeToken = TypeToken.of(String.class);
        TypeToken<Integer> integerTypeToken = TypeToken.of(Integer.class);

        //略
    }

}
