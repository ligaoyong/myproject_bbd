package com.bbd.stream;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wuyongchong on 2018/9/26.
 */
public class Distinct{
    static class A{
        private String name;
        private Integer age;

        public A(String name, Integer age) {
            this.name = name;
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getAge() {
            return age;
        }

        public void setAge(Integer age) {
            this.age = age;
        }

        @Override
        public String toString() {
            return "A{" +
                    "name='" + name + '\'' +
                    ", age=" + age +
                    '}';
        }

        @Override
        public int hashCode() {
            return this.getAge().hashCode();
        }

        @Override
        public boolean equals(Object obj) {
            return this.getAge() == ((A)obj).getAge();
        }
    }
    public static void main(String[] args) {
        List<A> list = new ArrayList();
        list.add(new A("xxx",15));
        list.add(new A("aaaa", 15));
        list.add(new A("aaaa", 16));
        list.stream().distinct().forEach(System.out::println);
    }
}
