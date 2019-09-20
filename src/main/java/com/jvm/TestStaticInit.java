package com.jvm;

public class TestStaticInit {

    public static void main(String[] args) {
        A object = new B();
        object = new B();
        /**
         * 输出：
         * A staic
         * B staic
         * A Constractor
         * B Constractor
         * A Constractor
         * B Constractor
         *
         * 为什么会有A Constractor的出现：因为在B的构造器内的第一行会隐式调用super()，即A的无参构造器
         */
    }
}
