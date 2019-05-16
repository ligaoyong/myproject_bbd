package com.rpc.simpleRPC;

import java.io.*;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * 简易RPC实现：服务消费方
 */
public class ServiceConsumer {
    public static void main(String[] args1) {
        //用JDK代理生成一个代理对象
        SayHelloService sayHelloService = (SayHelloService) Proxy.newProxyInstance(new SayHelloServiceImpl().getClass().getClassLoader(), new Class[]{SayHelloService.class},
                (Object proxy, Method method, Object[] args) -> {
                    Socket socket = new Socket();
                    socket.connect(new InetSocketAddress("0.0.0.0", 12345));
                    System.out.println(socket);
                    /**
                     * Socket[addr=/0.0.0.0,port=12345,localport=60753]
                     * Socket[addr=/0.0.0.0,port=12345,localport=60754]
                     */
                    try (OutputStream outputStream = socket.getOutputStream();
                         InputStream inputStream = socket.getInputStream();
                         //包装流 免得经常读字节流 比较麻烦
                         ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
                         ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
                    ) {
                        objectOutputStream.writeUTF(SayHelloService.class.getName()); //传递接口名称
                        objectOutputStream.writeUTF(method.getName());//传递方法名称
                        objectOutputStream.writeObject(method.getReturnType().getName());//传递参数类型
                        objectOutputStream.writeObject(args[0]);//传递参数值

                        Object o = objectInputStream.readObject(); //接受远程传递的结果值
                        return o;
                    }
                });
        String hello = sayHelloService.sayHello("hello");
        System.out.println(hello);
        String hell = sayHelloService.sayHello("hell");
        System.out.println(hell);
    }
}
