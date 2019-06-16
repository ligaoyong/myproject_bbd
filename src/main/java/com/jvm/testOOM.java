package com.jvm;

import com.google.common.collect.Lists;
import org.junit.Test;

import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.List;

public class testOOM {
    static class OOMObject{}

    public static void main(String[] args) {
        ArrayList<OOMObject> objects = Lists.newArrayList();
        while (true){
            objects.add(new OOMObject());
        }
    }

    /**
     * 查看垃圾收集器
     */
    @Test
    public void test1(){
        List<GarbageCollectorMXBean> l = ManagementFactory.getGarbageCollectorMXBeans();
        for(GarbageCollectorMXBean b : l) {
            System.out.println(b.getName());
        }
    }
}
