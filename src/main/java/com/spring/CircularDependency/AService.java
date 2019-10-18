package com.spring.CircularDependency;

/**
 * 循环依赖示例
 *
 * @author ligaoyong@gogpay.cn
 * @date 2019/10/18 10:17
 */
public class AService {
    private BService bService = new BService();

    public static void main(String[] args) {
        AService aService = new AService();
    }
}
