package com.guava.strings;

import com.google.common.base.CharMatcher;
import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import org.junit.Test;

/**
 * Created by wuyongchong on 2018/11/5.
 * String处理工具
 */
public class joinerAndSplitter {

    /**
     * Joiner 字符串连接工具
     * Joiner实例总是不可变的。joiner配置方法将始终返回一个新的joiner，
     * 您必须使用它来获得所需的语义。这使得任何接合器线程都是安全的，并且可以作为静态的最终常数使用
     */
    @Test public void test1(){
        Joiner joiner = Joiner.on(",").skipNulls(); //遇到null跳过
        String s = joiner.join("Harry", null, "Ron", "Hermione");
        System.out.println(s);  // Harry,Ron,Hermione

        Joiner joiner1 = Joiner.on(";").useForNull("--");   //遇到null用--代替
        String s1 = joiner1.join("Harry", null, "Ron", "Hermione");
        System.out.println(s1);
    }

    /**
     * Splitter 字符串切割工具
     *
     */
    @Test public void test2(){
        Iterable<String> strings = Splitter.on(',')
                .trimResults()  //排除切割后的前后空格    " aaa " -> "aaa"
                .omitEmptyStrings() //排除空串""
                .split("foo,bar,,   qux");
        strings.forEach(System.out::println);
    }

    /**
     * Splitter的创建
     */
    @Test public void test3(){
        Splitter.on(";");

        //Splitter.on(CharMatcher) 在某些类别中的任意字符的出现而分裂
        Splitter.on(CharMatcher.anyOf(",;/"));

        //正则
        Splitter.onPattern("[0-9]");

        //固定长度切开
        Splitter.fixedLength(4);
    }

    /**
     * Splitter 修改器
     */
    @Test public void test4(){
        /**
         * omitEmptyStrings() 自动从结果中删除空字符串
         * 例子：Splitter.on(',').omitEmptyStrings().split("a,,c,d") returns "a", "c", "d"
         */

        /**
         * trimResults() 从结果中删除空白   " a" -> "a"
         * Splitter.on(',').trimResults().split("a, b, c, d") returns "a", "b", "c", "d"
         */

        /**
         * trimResults(CharMatcher) 根据结果对匹配指定的CharMatcher的字符进行修剪。
         * Splitter.on(',')
         * .trimResults(CharMatcher.is('_'))
         * .split("_a ,_b_ ,c__")
         * returns "a ", "b_ ", "c".
         */

        /**
         * limit(int n) 返回指定数目的字符串后停止分裂。即分裂n-1次
         * Splitter.on(',').limit(3).split("a,b,c,d")
         * returns "a", "b", "c,d"
         */
    }

    /**
     * 如果希望获得列表，请使用splitToList()而不是split()。
     */
}
