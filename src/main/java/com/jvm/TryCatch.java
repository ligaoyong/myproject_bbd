package com.jvm;

/**
 * TODO
 *
 * @author ligaoyong@gogpay.cn
 * @date 2019/10/10 14:39
 */
public class TryCatch {
    public static void main(String[] args) {
        try {
            throw new NullPointerException();
        }catch (Exception e){
            throw new ArithmeticException("yichang");
        }finally {
            //final将在catch块中抛出异常之前执行 和return一样
            System.out.println("finally 执行");
        }
    }
}
