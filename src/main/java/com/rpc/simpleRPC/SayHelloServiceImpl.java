package com.rpc.simpleRPC;

public class SayHelloServiceImpl implements SayHelloService {
    @Override
    public String sayHello(String param) {
        if (param.contains("hello")){
            return "hello";
        }
        return "byby";
    }
}
