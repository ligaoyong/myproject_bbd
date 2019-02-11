package com.generics;

import com.google.common.collect.Maps;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wuyongchong on 2018/11/13.
 * 类型安全的异构容器
 *  key -->> value
 * class-->>Object
 */
public class Favorites {

    private Map<Class<?>, Object> map = new HashMap<>();

    public <T> void put(Class<T> clazz,Object object){
        if(clazz.isInstance(object)){
            map.put(clazz, object);
        }else
            throw new IllegalArgumentException("类型不一致");
    }

    public <T> T get(Class<T> clazz){
        Object o = map.get(clazz);
        return clazz.cast(o);   // 根据class和Object 返回特定类型对象
        //return (T) o; 强制转型也可以 只要保证对象的类型正确 否则抛出ClassCastException
    }

    @Test public void test1(){
        put(Integer.class, 1);
        Integer integer = get(Integer.class);
        System.out.println(integer);

        put(String.class, "hehe");
        String s = get(String.class);
        System.out.println(s);

        //put(String.class,1);//实例类型与class不一致 会抛出异常
    }
}
