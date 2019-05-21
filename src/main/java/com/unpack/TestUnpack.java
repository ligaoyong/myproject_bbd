package com.unpack;

import org.junit.Test;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 测试socket下的 拆包与粘包
 *  bio下只要不是面向字节流的 就不会产生粘包与拆包
 *  我们使用ObjectOutputStream 底层自动处理拆包与粘包
 *  而nio下(netty)因为client与server端都是面向bytebuffer而 如果不控制字节顺序的话
 *  则会产生粘包与拆包
 */
public class TestUnpack {

    @Test
    public void client() throws IOException {
        Socket socket = new Socket();
        socket.connect(new InetSocketAddress(9999));
        ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
        for(int i=0;i<1000;i++){
            outputStream.writeUTF("你好，欢迎关注我的微信公众号，《闪电侠的博客》!");
        }
    }

    @Test
    public void server() throws IOException {
        ServerSocket serverSocket = new ServerSocket();
        serverSocket.bind(new InetSocketAddress(9999));
        while (true){
            Socket socket = serverSocket.accept();
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            String s;
            while (!StringUtils.isEmpty(s = objectInputStream.readUTF())){
                System.out.println("服务端收到："+s);
            }
        }
    }

}
