package com.jvm;

public class B extends A {
    static {
        System.out.println("B staic");
    }

    public B(){
        System.out.println("B Constractor");
    }
}
