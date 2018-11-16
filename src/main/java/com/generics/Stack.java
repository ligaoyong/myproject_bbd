package com.generics;

import com.google.common.collect.Lists;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by wuyongchong on 2018/11/12.
 * 使用通配符增加api的灵活性
 */
public class Stack<E> {
    private Object[] elements;
    private E[] eles;
    private Integer length = 0;

    public Stack(){
        elements = new Object[100];
        eles = (E[]) elements;//直接将Object[] 强制转换为E[]
    }

    public E pop(){
        return eles[length-1];
    }

    public void push(E e){
        eles[length] = e;
        length++;
    }

    /**
     * 这种方式有缺陷 参数src的泛型只能严格匹配E类型 而不能匹配E类型的子类型
     * 比如E为Object，src的泛型是Integer。则没有严格匹配 会报错
     * @param src
     */
    public void pushAll(Iterable<E> src){
        for (E e:src) {
            push(e);
        }
    }

    /**
     * 这种方式是最完美的方式，此时类型通配符可以匹配E和E的子类型
     * @param src
     */
    public void pushAll2(Iterable<? extends E> src){
        for (E e:src) {
            push(e);
        }
    }

    /**
     * 这种方式也有缺陷 只有当dst中的类型与E严格一致的情况下才能放入
     * 如果dst中的类型是Object 而E是Integer则会报错，这明显违背了集合泛型的规则
     * @param dst
     */
    public void popAll(Collection<E> dst){
        for (Object e:elements) {
            dst.add((E) e);
        }
    }

    /**
     * 这种方式完美解决popAll的缺陷 此时只要E是dst中元素类型的子类型 即可放入集合dst中
     * 这才符合集合泛型的规则
     * @param dst
     */
    public void popAll2(Collection<? super E> dst){
        for (Object e:elements) {
            dst.add((E) e);
        }
    }

    @Test public void test1(){
        Stack<Object> stack = new Stack<>();
        ArrayList<Integer> list = Lists.newArrayList();
        list.add(1);
        //stack.pushAll(list); 此时会编译不过 因为Integer不能严格匹配Object类型
        stack.pushAll2(list);   //完美编译、运行
        System.out.println(stack.pop());
    }

    @Test public void test2(){
        Stack<Integer> stack = new Stack<>();
        stack.push(1);
        ArrayList<Object> list = Lists.newArrayList();
        //stack.popAll(list); 编译报错 此时Stack的类型是Integer 与集合list的Object 没有严格一致
        stack.popAll2(list);    // 此时完美通过 因为Object super Integer 成立
        System.out.println(list);
    }
}
