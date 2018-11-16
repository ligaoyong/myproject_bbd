package com.generics;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import org.junit.Test;
import org.springframework.context.annotation.EnableLoadTimeWeaving;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by wuyongchong on 2018/11/12.
 * 泛型与参数变量的组合
 */
public class VarargsAndGenerics {

    private static void dangerous(List<String>... stringLists){
        List<Integer> iniList = Lists.newArrayList(1);
        Object[] objects = stringLists;
        objects[0] = iniList;
        String s = stringLists[0].get(0);
    }

    @Test public void test1(){
        ArrayList<String> list1 = Lists.newArrayList();
        list1.add("a");
        list1.add("b");
        ArrayList<String> list2 = Lists.newArrayList("c", "d");
        dangerous(list1,list2);
    }

    /**
     * 这种方法返回的是Object[] 会存在类型安全问题
     * @param t
     * @param <T>
     * @return
     */
    private static <T> T[] toArray(T... t){
        return t; //此时返回的是Object[]
    }

    /**
     * 这种方式才是类型安全的
     * @param t
     * @param <T>
     * @return
     */
    private static <T> List<T> toArray2(T... t){
        ArrayList<T> list = Lists.newArrayList();
        for (Object e:t){
            list.add((T) e);
        }
        return list;
    }

    @Test public void test2(){
        Integer[] integers = toArray(1, 2, 3, 4);
        System.out.println(integers);
    }

    static <T> T[] pickTwo(T a, T b, T c) {
        switch(ThreadLocalRandom.current().nextInt(3)) {
            case 0: return toArray(a, b);
            case 1: return toArray(a, c);
            case 2: return toArray(b, c);
        }
        throw new AssertionError(); // Can't get here
    }

    static <T> List<T> pickTwo2(T a, T b, T c) {
        switch(ThreadLocalRandom.current().nextInt(3)) {
            case 0: return toArray2(a, b);
            case 1: return toArray2(a, c);
            case 2: return toArray2(b, c);
        }
        throw new AssertionError(); // Can't get here
    }

    @Test public void test3(){
        //[Ljava.lang.Object; cannot be cast to [Ljava.lang.String;
        //pickTwo返回的时Object[] 不能强制为String[]
        String[] attributes = pickTwo("Good", "Fast", "Cheap");
        System.out.println(attributes);
    }

    @Test public void test4(){
        //运行通过
        List<String> list = pickTwo2("Good", "Fast", "Cheap");
        System.out.println(list.toString());
    }
}
