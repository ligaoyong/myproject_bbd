package com.guava.collections;

import com.google.common.collect.*;
import com.google.common.primitives.Ints;
import org.junit.Test;

import java.util.*;

/**
 * Created by wuyongchong on 2018/11/2.
 * 集合的一些工具类
 */
public class Collection_Utilities {

    //静态创建器
    @Test public void test1(){
        List<String> theseElements = Lists.newArrayList("alpha", "beta", "gamma");
    }

    //Lists -> 提供额外的操作
    @Test public void test2(){
        //除了静态构造函数方法和函数式编程方法之外，List还提供了许多关于List对象的有用方法。
        List<Integer> countUp = Ints.asList(1, 2, 3, 4, 5);
        List<Integer> countDown = Lists.reverse(countUp); // {5, 4, 3, 2, 1}

        List<List<Integer>> parts = Lists.partition(countUp, 2); // {{1, 2}, {3, 4}, {5}}
    }

    //Sets ->提供集合运算
    @Test public void test3(){
        Set<String> wordsWithPrimeLength = ImmutableSet.of("one", "two", "three", "six", "seven", "eight");
        Set<String> primes = ImmutableSet.of("two", "three", "five", "seven");
        //交集
        Sets.SetView<String> intersection = Sets.intersection(primes, wordsWithPrimeLength);
        System.out.println(intersection);
        //并集
        Sets.SetView<String> union = Sets.union(wordsWithPrimeLength, primes);
        System.out.println(union);
        //差集
        Sets.SetView<String> difference = Sets.difference(wordsWithPrimeLength, primes);
        System.out.println(difference);
        //笛卡尔集
        Set<List<String>> cartesianProduct = Sets.cartesianProduct(wordsWithPrimeLength, primes);
        System.out.println(cartesianProduct);
        //数量？
        Set<Set<String>> powerSet = Sets.powerSet(primes);
        System.out.println(powerSet);
    }

    //Maps -> 特殊操作
    @Test public void test4(){
        //difference()允许您比较两个映射之间的所有差异。它返回一个MapDifference对象，该对象将Venn图分解为
        Map<String, Integer> left = ImmutableMap.of("a", 1, "b", 2, "c", 3);
        Map<String, Integer> right = ImmutableMap.of("b", 2, "c", 4, "d", 5);

        MapDifference<String, Integer> difference = Maps.difference(left, right);

        System.out.println(difference.entriesInCommon());   //返回相同的
        System.out.println(difference.entriesDiffering());  //键相同但值不同的项
        System.out.println(difference.entriesOnlyOnLeft()); //仅在左边的
        System.out.println(difference.entriesOnlyOnRight());//仅在右边的
    }

    //Multisets -> 操作Multiset的工具类
    @Test public void test5(){
        //标准的收集操作，例如containsAll，忽略了多集中的元素计数，只关心元素是否在多集中。
        //multiset提供了许多在multiset中考虑到元素倍数的操作。
        HashMultiset<Object> multiset = HashMultiset.create();
        multiset.add("a", 2);
        System.out.println(multiset);

    }

    //Multimaps -> 操作Multimap的工具类
    
}
