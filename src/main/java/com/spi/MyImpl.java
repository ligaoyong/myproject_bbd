package com.spi;

/**
 * 假设这是我们引入某个框架后 实现了他的某个接口
 * 那么我们只需要在指定的目录下声明这个类，这个类就会被框架调用
 * 加载数据库驱动也用到了类似的机制，且还破坏了双亲委派机制
 */
public class MyImpl implements MyInterface {
}
