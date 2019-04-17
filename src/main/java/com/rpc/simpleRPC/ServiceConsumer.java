package com.rpc.simpleRPC;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * 简易RPC实现：服务消费方
 */
public class ServiceConsumer {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Socket socket = new Socket();
        socket.connect(new InetSocketAddress("0.0.0.0", 12345));
        try (OutputStream outputStream = socket.getOutputStream();
             InputStream inputStream = socket.getInputStream();
             //包装流 免得经常读字节流 比较麻烦
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
             ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
        ) {
            objectOutputStream.writeUTF(SayHelloService.class.getName()); //传递接口名称
            objectOutputStream.writeUTF("sayHello");//传递方法名称
            objectOutputStream.writeObject(String.class.getName());//传递参数类型
            objectOutputStream.writeObject("byby");//传递参数值

            Object o = objectInputStream.readObject(); //接受远程传递的结果值
            System.out.println(o);
        }
    }
}
