package com.guava.collections;

import com.google.common.collect.*;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by wuyongchong on 2018/11/2.
 * guava提供的一些新的集合类型
 */
public class New_Collection_Type {

    //Multiset  多重集合（数学意义上的集合，元素可重复）
    @Test
    public void test1(){
        /*番石榴提供了一种新的集合类型Multiset，它支持添加多个元素。
        维基百科将多集定义为数学上的“集合”，即“允许成员出现不止一次的集合概念的概括”。
        在多集中，就像在集合中一样，与元组相反，元素的顺序是不相关的:多集{a, a, b}和{a, b, a}是相等的*/

        //有两种方式来看待这个问题
        //1、这就像没有排序约束的ArrayList:排序无关紧要。
        //2、这就像一个Map，带有元素和计数。
        ArrayList<String> list = Lists.newArrayList("a", "b", "c", "d", "a", "b", "c", "a");
        HashMultiset<String> multiset = HashMultiset.create(list);

        System.out.println(multiset.count("a"));    //返回元素的个数
        System.out.println(multiset.elementSet());  //过滤掉重复的
        System.out.println(multiset.entrySet());    //元素->个数的map

    }

    //MultiMap  多重映射
    @Test public void test(){
        //Guava的Multimap框架可以轻松处理从键到多个值的映射 替代Map<K, List<V>>或者Map<K, Set<V>>
        //很少直接使用Multimap接口;更多情况下，使用ListMultimap或SetMultimap，它们分别将键映射到列表或集合

        // 键使用tree保存  值用arrayList保存
        ListMultimap<String, Integer> treeListMultimap =
                MultimapBuilder.treeKeys().arrayListValues().build();

        //键使用hash映射保存 值使用hashSet保存
        SetMultimap<String, Integer> hashEnumMultimap =
                MultimapBuilder.hashKeys().hashSetValues().build();


        treeListMultimap.put("A", 1);
        treeListMultimap.put("A", 11);
        treeListMultimap.put("B", 2);
        treeListMultimap.put("B", 22);

        List<Integer> list = treeListMultimap.get("A");
        System.out.println(list);
    }

    //BiMap 提供额外的值映射键的方式
    @Test
    public void test2(){
        //string --> Integer
        BiMap<String, Integer> nameToAge = HashBiMap.create();

        nameToAge.put("a", 10); //值必须都唯一 否则抛出异常
        nameToAge.forcePut("a", 20); //强制put 如果存在相同的值 则已存在的kv对会被删除

        //反转成Integer --> string
        BiMap<Integer, String> ageToName = nameToAge.inverse();

    }

    //Table
    @Test
    public void test3(){
        //Table<行，列，值>
        Table<String, String, Double> weightedGraph = HashBasedTable.create();
        weightedGraph.put("r1", "c2", 4.0);
        weightedGraph.put("r1", "c3", 20.0);
        weightedGraph.put("r2", "c3", 5.0);
        /** 类似于下面这张表
         *      c1   c2  c3
         *  r1      4.0 20.0
         *  r2          5.0
         *  r3
         */
        Map<String, Double> row1 = weightedGraph.row("r1");// returns a Map mapping v2 to 4, v3 to 20
        Map<String, Double> column3 = weightedGraph.column("c3");// returns a Map mapping v1 to 20, v2 to 5
        System.out.println(row1);
        System.out.println(column3);
    }

    //ClassToInstanceMap 键是class类型 值是对应的实例类型

}
