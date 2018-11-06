package com.guava.hash;

import com.google.common.base.Charsets;
import com.google.common.hash.*;
import org.junit.Test;

/**
 * Created by wuyongchong on 2018/11/6.
 * hash算法工具
 */
public class HashingTest {

    /**
     * Objects.hashCode()实现趋向于非常快，但是具有较弱的冲突预防能力，并且不期望位分散。
     * 这使得它们非常适合在哈希表中使用，因为额外的冲突只会对性能造成轻微的影响，
     * 而使用二级哈希函数(Java中所有合理的哈希表实现都使用二级哈希函数)很容易纠正糟糕的位分散。
     */
    /**
     * 然而，对于除简单哈希表之外的许多哈希函数的使用，Objects.hashCode几乎总是不够用
     */
    @Test public void test1(){
        //获取hash函数(md5)
        HashFunction hf = Hashing.md5();

        class Person {
            int id;
            String firstName;
            String lastName;
            int birthYear;
        }
        //描述了如何将一个对象进行hash计算
        Funnel<Person> personFunnel = new Funnel<Person>() {
            @Override
            public void funnel(Person from, PrimitiveSink into) {
                into.putInt(from.id)
                        .putString(from.firstName,Charsets.UTF_8)
                        .putString(from.lastName,Charsets.UTF_8)
                        .putInt(from.birthYear);
            }
        };

        HashCode hc = hf.newHasher()
                .putLong(1)
                .putString("张三", Charsets.UTF_8)
                .putObject(new Person(), personFunnel)
                .hash();

        System.out.println(hc);

        //将hashCode转换了其他数据类型
        hc.asBytes();
        hc.asLong();
        /**
         * HashFunction是一个纯的无状态函数，它将任意数据块映射到固定数量的位，
         * 其属性是，相等的输入总是产生相等的输出，而不相等的输入总是产生不相等的输出
         */
        /**
         * Hasher
         * 哈希函数可以请求有状态的Hasher，它提供了流畅的语法来向哈希添加数据，然后检索哈希值
         * Hasher可以接受任何原始输入、字节数组、字节数组的切片、字符序列、某些字符集中的字符序列等等，
         * 或任何其他对象，并提供适当的漏斗(Funnel)
         */
        /**
         * Funnel
         * 漏斗描述如何将特定对象类型分解为基本字段值
         */
    }

    /**
     * 常用hash函数
     md5()
     murmur3_128()
     murmur3_32()
     sha1()
     sha256()
     sha512()
     goodFastHash(int bits)
     */
}
