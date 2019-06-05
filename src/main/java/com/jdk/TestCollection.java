package com.jdk;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * hashMap
 */
public class TestCollection {

    @Test
    public void test1(){
        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put("Aa", "1");
        hashMap.put("BB", "2"); //Aa BB的hash值相同
        hashMap.put("Aa", "3");

        //下面的key的hash值都相同，测试树化
        hashMap.put("轷龚", "轷龚");
        hashMap.put("轸齻", "轸齻");
        hashMap.put("轹齜", "轹齜");
        hashMap.put("轺鼽", "轺鼽");
        hashMap.put("轻鼞", "轻鼞");
        hashMap.put("轼黿", "轼黿");
        hashMap.put("载黠", "载黠");
        hashMap.put("轾黁", "轾黁");
        hashMap.put("轿麢", "轿麢"); //大于8个 由链表构造二叉树 但是数组长度小于64 因此只扩容一倍 32
        hashMap.put("辀麃", "辀麃"); //大于8个 由链表构造二叉树 但是数组长度小于64 因此只扩容一倍 64
        hashMap.put("辁鹤", "辁鹤"); //大于8个 由链表构造二叉树 真正的开始树化 因此要达到树化 最少需要11个hash相等的元素

        hashMap.get("a");
    }

    @Test public void test2(){
        ConcurrentHashMap<String, String> concurrentHashMap = new ConcurrentHashMap<>();
        concurrentHashMap.put("Aa", "Aa");
        concurrentHashMap.put("BB", "BB");
    }

    @Test public void test3(){
        HashSet<String> hashSet = new HashSet<>();
        hashSet.add("Aa");
        hashSet.add("BB");

    }

    @Test public void test4(){
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("Aa");
    }

    @Test public void test5(){
        CopyOnWriteArrayList<String> copyOnWriteArrayList = new CopyOnWriteArrayList<>();
        copyOnWriteArrayList.add("Aa");
    }
}
