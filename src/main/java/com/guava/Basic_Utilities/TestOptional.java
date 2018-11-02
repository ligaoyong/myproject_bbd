package com.guava.Basic_Utilities;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by wuyongchong on 2018/11/1.
 */
public class TestOptional {

    /*Optional创建的方式*/
    @Test
    public void make_Optional(){
        Optional<Integer> empty = Optional.empty();//返回空
        Optional<Object> o = Optional.ofNullable(null);//传入的可为空
        Optional<Integer> optional = Optional.of(5);//传入的必须不为空

        optional.ifPresent(System.out::println);
        System.out.println(optional.get()); //获取值
        System.out.println(o.orElse(10));   // 获取值 若不存在 返回10
        System.out.println(o.orElseGet(()->Integer.valueOf(100))); //获取值 若不存在 返回供给接口给的值
        //System.out.println(o.orElseThrow(()-> new NullPointerException("空"))); //获取值 若不存在 抛出供给接口提供的异常
        //System.out.println(optional.filter(x -> x > 10).get()); //过滤掉值
        Optional<String> optional1 = optional.map(x -> "hehe:" + x); //对元素进行操作 返回一个新类型的Optional

        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);

        Optional<List<Integer>> listOptional = Optional.of(list);
//        listOptional.flatMap(x -> "haha"+x)
    }

}
