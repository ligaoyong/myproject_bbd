package com.NIO.socketNetty;

import jline.ConsoleReader;
import jline.ConsoleReaderInputStream;

import java.io.*;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.Scanner;

/**
 * BIO socket连接netty服务
 *
 * @author ligaoyong@gogpay.cn
 * @date 2019/9/30 16:52
 */
public class SocketClient {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("127.0.0.1", 8888);
        System.out.println(socket.isConnected());
        InputStream inputStream = socket.getInputStream();
        OutputStream outputStream = socket.getOutputStream();

        Runnable reveive = () -> {
            byte[] bytes = new byte[1024];
            int num = 0;
            try {
                while ((num=inputStream.read(bytes))!= -1){
                    System.out.println("收到数据："+new String(bytes));
                }
            }catch (Exception e){
                System.out.println(e);
            }
        };
        Runnable send = () -> {
            try {
                String data = "hello";
                /**
                 * 这里非常重要 socket要与netty服务端相连 数据的交互方式必须按照ByteBuffer来
                 * netty服务端第一个处理器必须是StringDecoder，以便把ByteBuffer解码为String
                 * netty服务端最后一个处理器也必须是StringEncoder，不然客户端收不到数据
                 */
                ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                byteBuffer.clear();
                byteBuffer.put(data.getBytes());
                byteBuffer.flip();
                outputStream.write(byteBuffer.array());
                System.out.println("已发送：" + data);
            } catch (IOException e) {
                e.printStackTrace();
            }
        };
        new Thread(reveive).start();
        new Thread(send).start();
    }
}
