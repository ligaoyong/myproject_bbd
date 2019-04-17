package com.rpc.simpleRPC;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 简易RPC实现：服务提供者
 */
public class ServiceProvider {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket();
        serverSocket.bind(new InetSocketAddress("0.0.0.0", 12345),200); //连接请求队列长度200
        ExecutorService executorService = Executors.newCachedThreadPool();

        //生产中 根据接口名称从容器中查找实现类
        ConcurrentHashMap<String, Object> stringObjectConcurrentHashMap = new ConcurrentHashMap<>();
        stringObjectConcurrentHashMap.put("com.rpc.simpleRPC.SayHelloService", new SayHelloServiceImpl());

        while (true) {
            Socket socket = serverSocket.accept();
            System.out.println(socket.toString());
            /**
             * Socket[addr=/192.168.139.139,port=60753,localport=12345]
             * Socket[addr=/192.168.139.139,port=60754,localport=12345] 源端口会被客户端随机设置
             */
            executorService.execute(() -> {
                try {
                    try (ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
                         ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());) {

                        String interfaceName = objectInputStream.readUTF();//接口名
                        String method = objectInputStream.readUTF();//方法名
                        Object paramType = objectInputStream.readObject();//参数类型
                        Object param = objectInputStream.readObject();//参数值

                        Object o = stringObjectConcurrentHashMap.get(interfaceName);//获取实现对象

                        Class<?> aClass = Class.forName(interfaceName);
                        Method method1 = aClass.getMethod(method, paramType.getClass());//通过反射获取方法
                        method1.setAccessible(true);

                        Object result = method1.invoke(o, param);   //调用方法

                        objectOutputStream.writeObject(result);//返回结果给调用者
                        socket.close();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

        }
    }
}
