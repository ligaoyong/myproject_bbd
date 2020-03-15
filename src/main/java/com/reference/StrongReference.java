package com.reference;

/**
 * 测试强引用
 *
 * @author ligaoyong@gogpay.cn
 * @date 2020/3/9 16:27
 */
public class StrongReference {

    static class Student{
        @Override
        protected void finalize() throws Throwable {
            System.out.println("我被回收了");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Student object = new Student();
        System.out.println(object);
        System.gc();
        //只要还有应用指向这个对象，这个对象就不会被jvm回收
        System.out.println(object);

        //可达性分析算法找不到对象 可以回收
        object = null;
        System.gc();
        Thread.sleep(3000);
    }
}
