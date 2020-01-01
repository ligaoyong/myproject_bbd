package com.jvm;

/**
 * 测试一下jdk的类加载机制 并破坏类加载机制
 */
public class TestClassLoader {

    public static void main(String[] args) {
        ClassLoader classLoader = TestClassLoader.class.getClassLoader(); //launcher$AppClassLoader -- 应用程序类加载器
        ClassLoader extClassLoader = classLoader.getParent(); //launcher$ExtClassLoader -- 扩展累加载器
        ClassLoader parent1 = extClassLoader.getParent(); // null extClassLoader的父类是BootstrapClassLoader

        //使用extClassLoader去加载应用程序classpath下的类
        try {
            Class<?> clazz = extClassLoader.loadClass("com.jvm.TestClassLoader");
            System.out.println("clazz : "+clazz);
        } catch (ClassNotFoundException e) {
            //会抛出异常 因为ExtClassLoader只会加载java_home/jre/lib/ext目录下 或者java.ext.dirs属性指定的目录下的类
            e.printStackTrace();
        }

        System.out.println("java.ext.dirs : "+System.getProperty("java.ext.dirs"));
        System.setProperty("java.ext.dirs",
                System.getProperty("java.ext.dirs") + ";D:\\idea_workspace\\myproject_bbd\\src\\main\\java\\com\\jvm");
        System.out.println("java.ext.dirs : "+System.getProperty("java.ext.dirs"));

        //设置好属性后 在进行加载
        try {
            Class<?> clazz = extClassLoader.loadClass("TestClassLoader");
            System.out.println("clazz : "+clazz);
        } catch (ClassNotFoundException e) {
            //会抛出异常 因为ExtClassLoader只会加载java_home/jre/lib/ext目录下 或者java.ext.dirs属性指定的目录下的类
            e.printStackTrace();
        }
    }

}
