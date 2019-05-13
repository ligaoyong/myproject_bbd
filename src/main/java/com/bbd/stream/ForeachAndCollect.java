package com.bbd.stream;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.io.Files;
import org.junit.Test;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

/**
 * Created by wuyongchong on 2018/11/19.
 * foreach操作与Collect操作对比
 */
public class ForeachAndCollect {

    //计数功能
    @Test public void test1(){
        HashMap<String, Long> map = Maps.newHashMap();
        ArrayList<String> list = Lists.newArrayList("a", "a", "b", "c", "d", "d", "e","a");

        //使用foreach来计数
        list.forEach(x -> map.merge(x,1L,Long::sum));
        System.out.println(map.toString());
        /*list.removeIf((x) -> {
            return true;
        });*/

        //使用collect来计数
        Map<String, Long> collect = list.stream().collect(Collectors.groupingBy(x -> x, counting()));
        System.out.println(collect.toString());

        //排序
        //comparing()的第一个参数String::toString 用于从流元素中提取用于排序的键 本例中就按照流元素作为排序键
        //第二个参数就是排序函数
        List<String> collect1 = list.stream().
                sorted(Comparator.comparing(String::toString,String::compareTo)).collect(Collectors.toList());
        System.out.println(collect1);

        //收集到map中
        //toMap()的第一个参数Object::toString 是键(需要从流中元素映射，本例使用流中的元素作为键)
        //toMap()的第二个参数String::toUpperCase 是值(也需要从流中进行映射，本例是将转换成大写)
        Map<String, String> map1 = list.stream().distinct().collect(Collectors.toMap(Object::toString, String::toUpperCase));
        System.out.println(map1.toString());    //{a=A, b=B, c=C, d=D, e=E}

        //分类器
        //words -> words 表示一个分类器classifier 它返回流中每个元素所属的类别 本例就按照自身分类
        Map<String, List<String>> collect2 = list.stream().collect(groupingBy(words -> words));
        System.out.println(collect2.toString());    // {a=[a, a, a], b=[b], c=[c], d=[d, d], e=[e]}

        //下游收集器：在每个类别的List上所做的操作
        //如counting 将元素出现的次数相加 而不是返回一个列表
        Map<String, Long> collect3 = list.stream().collect(groupingBy(x -> x, counting()));
        System.out.println(collect3.toString());    // {a=3, b=1, c=1, d=2, e=1}

    }

}
