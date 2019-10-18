package com.spring.CircularDependency;

/**
 * TODO
 *
 * @author ligaoyong@gogpay.cn
 * @date 2019/10/18 10:17
 */
public class BService {
    private AService aService = new AService();
}
