package com.NIO.socketNetty;

import jline.ConsoleReader;
import jline.ConsoleReaderInputStream;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

/**
 * BIO socket连接netty服务
 *
 * @author ligaoyong@gogpay.cn
 * @date 2019/9/30 16:52
 */
public class SocketClient {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("0.0.0.0", 8888);
        ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
        ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());

        Runnable reveive = () -> {
            Scanner scanner = new Scanner(inputStream);
            while (scanner.hasNext()){
                System.out.println("接收到："+scanner.next());
            }
        };
        Runnable send = ()->{
            try {
                Scanner scanner = new Scanner(new ConsoleReaderInputStream(new ConsoleReader()));
                while (scanner.hasNext()){
                    String next = scanner.next();
                    outputStream.writeUTF(next);
                    System.out.println("已发送："+next);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        };
        new Thread(reveive).start();
        new Thread(send).start();

    }
}
