package com.bbd.builder;

/**
 * Created by lgy on 2018/9/17.
 */
public class testBuilder {
    public static void main(String[] args) {
        //获取构造者对象
        Person.Builder builder = new Person.Builder(1L, "张三");
        //链式设置属性
        builder.age(22).highLength("180").sex(1);
        //调用构造者的构造方法构造对象
        Person person = builder.build();
        System.out.println(person);
    }
}
