package com.enumAndAnnotation;

/**
 * Created by lgy on 2018/11/16.
 * 测试每种枚举常量的同一种方法的不同操作
 */
public enum Operation {

    PLUS("+") {
        public double apply(double x, double y) { return x + y; }
    },
    MINUS("-") {
        public double apply(double x, double y) { return x - y; }
    },
    TIMES("*") {
        public double apply(double x, double y) { return x * y; }
    },
    DIVIDE("/") {
        public double apply(double x, double y) { return x / y; }
    };

    private final String symbol;

    Operation(String symbol) { this.symbol = symbol; }

    @Override public String toString() { return symbol; }

    //声明一个抽象方法，在每个实例上去实现它
    public abstract double apply(double x, double y);

}
