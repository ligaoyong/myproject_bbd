package com.enumAndAnnotation;

import java.util.EnumMap;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by lgy on 2018/11/16.
 */
public class Test {
    @org.junit.Test
    public void test1(){
        Apple fuji = Apple.FUJI;
        System.out.println(fuji);   // FUJI
        System.out.println(fuji.name());    //FUJI
        System.out.println(fuji.ordinal()); // 0

        System.out.println(fuji.getSize());
        fuji.setSize(100);
        System.out.println(fuji.getSize());

        System.out.println(fuji.getMsg());  //a
    }

    //测试八大行星
    @org.junit.Test public void test2(){
        double earthWeight = Double.parseDouble("20");
        double mass = earthWeight / Planet.EARTH.surfaceGravity();
        for (Planet p : Planet.values())
            System.out.printf("Weight on %s is %f%n",
                    p, p.surfaceWeight(mass));
    }

    //测试不同枚举的在同一方法上的不同操作
    @org.junit.Test public void test3(){
        for (Operation op :Operation.values()) {
            double x = 2;
            double y = 4;
            System.out.printf("%f %s %f = %f%n",
                    x, op, y, op.apply(x, y));
        }
    }

    @org.junit.Test public void test4(){
        //使用EnumSet
        EnumSet<Planet> enumSet = EnumSet.allOf(Planet.class);
        enumSet.forEach(System.out::println);

        System.out.println("-------------");
        //使用原始的set比较麻烦
        Set<Planet> set = new HashSet<>();
        set.add(Planet.EARTH);
        set.add(Planet.JUPITER);
        set.forEach(System.out::println);

        System.out.println("++++++++++++++++++++");

        EnumMap<Planet, Planet> enumMap = new EnumMap<Planet, Planet>(Planet.class);
        enumMap.put(Planet.EARTH, Planet.JUPITER);
    }
}
