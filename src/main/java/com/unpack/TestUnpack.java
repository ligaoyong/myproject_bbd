package com.unpack;

import org.junit.Test;
import org.springframework.util.StringUtils;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

/**
 * 测试socket下的 拆包与粘包
 *  bio下只要不是面向字节流的 就不会产生粘包与拆包
 *  我们使用ObjectOutputStream 底层自动处理拆包与粘包
 *  而nio下(netty)因为client与server端都是面向bytebuffer而 如果不控制字节顺序的话
 *  则会产生粘包与拆包
 */

/**
 * 最后还演示了tcp底层的拆包与粘包机制
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




    /**
     * 面向字节流得拆包与粘包现象及解决方案
     * 解决方案：在每个数据包的开头添加报文头(数据包的长度)
     * @throws IOException
     */
    @Test
    public void testServer() throws IOException {
        ServerSocket serverSocket = new ServerSocket();
        serverSocket.bind(new InetSocketAddress(9999));
        while (true){
            Socket socket = serverSocket.accept();
            InputStream inputStream = socket.getInputStream();

            //重点api：inputStream.read() 读取下一个字节的数据(是真的数据，以int的形式返回 范围在-1，255之间)：也就是说一次读取一个字节
            //如果是-1 则表示没有数据可读 流已经到达终点(流被关闭)，否则阻塞读取
            //如果不是-1，则表示读到的是数据 我们需要把int转为byte，因为byte才是真正的数据
            //所有的api都是调用这个api来实现的

            /**
             * 不加控制的读取 在数据很大的时候 会出现粘包 拆包的问题
             * 因为tcp是数据流，当客户端连续发送的数据且大小不统一时，服务端无法区分数据的界限
             * 具体看：tcp粘包、拆包原理
             */
            /*byte[] bytes = new byte[1350];
            while ((inputStream.read(bytes)) != -1){ //有数据可读
                int read = inputStream.read(bytes);
                System.out.println("读取到 ： " + read + "字节");
                System.out.println(new String(bytes));
            }*/

            /**
             * 带控制的读取 先读取前四个字节 这四个字节表示真实数据的长度(报文头)
             * 在往后读取指定长度的数据(报文体)
             * 往复如此 则不会出现拆包、粘包的问题
             *
             * 注意：使用sockeInputStream读取一个流的数据时，流的开头会出现4个字节的脏数据，分别是{-84,-19,0,5}
             *      需要把开头的4个字节的脏数据排除掉
             */
            byte[] zang = new byte[4]; //不知道为什么 流的开头会多出来4个字节的脏数据
            inputStream.read(zang, 0, 4); //把脏数据读出来

            byte[] length = new byte[4];
            while (inputStream.read(length) == 4){ // 读取前4个字节 这4个字节表示数据的长度
                //byte[] 转化为长度int
                int value = 0;
                for(int i = 0; i < 4; i++) {
                    int shift= (3-i) * 8;
                    value += (length[i] & 0xff) << shift;
                }
                //data存放真实的数据
                byte[] data = new byte[value];
                int len;
                if ((len = inputStream.read(data)) == value) { //读取到正确的数据
                    System.out.println("读取到 ： " + len + "字节");
                    System.out.println(new String(data));
                }else
                    System.out.println("数据的长度不够,length : "+length.length+",真实读取到的len ："+len);

            }
        }
    }

    @Test
    public void testClient() throws IOException {
        Socket socket = new Socket();
        socket.connect(new InetSocketAddress(9999));
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
        OutputStream outputStream = socket.getOutputStream();
        String s1 = "Start:你好，欢迎关注我的微信公众号，《闪电侠的博客》!End";
        String s2 = "Start:你好，欢迎关注我的微信公众号，《闪电侠的博客》!" +
                "随着智能硬件越来越流行，很多后端开发人员都有可能接触到socket编程。" +
                "而很多情况下，服务器与端上需要保证数据的有序，稳定到达，自然而然就会选择基于tcp/ip协议的socekt开发。" +
                "而很多情况下，服务器与端上需要保证数据的有序，稳定到达，自然而然就会选择基于tcp/ip协议的socekt开发。" +
                "而很多情况下，服务器与端上需要保证数据的有序，稳定到达，自然而然就会选择基于tcp/ip协议的socekt开发。" +
                "而很多情况下，服务器与端上需要保证数据的有序，稳定到达，自然而然就会选择基于tcp/ip协议的socekt开发。" +
                "而很多情况下，服务器与端上需要保证数据的有序，稳定到达，自然而然就会选择基于tcp/ip协议的socekt开发。" +
                "开发过程中，经常会遇到tcp粘包，拆包的问题，本文将从产生原因，" +
                "和解决方案以及workerman是如何处理粘包拆包问题的，这几个层面来说明这个问题什么是粘包拆包" +
                "对于什么是粘包、拆包问题，我想先举两个简单的应用场景：" +
                "客户端和服务器建立一个连接，客户端发送一条消息，客户端关闭与服务端的连接。" +
                "客户端和服务器简历一个连接，客户端连续发送两条消息，客户端关闭与服务端的连接。" +
                "对于第一种情况，服务端的处理流程可以是这样的：当客户端与服务端的连接建立成功之后，" +
                "服务端不断读取客户端发送过来的数据，当客户端与服务端连接断开之后，服务端知道已经读完了一条消息，" +
                "然后进行解码和后续处理...。对于第二种情况，如果按照上面相同的处理逻辑来处理，那就有问题了，" +
                "我们来看看第二种情况下客户端发送的两条消息递交到服务端有可能出现的情况：End";
        System.out.println("s1 :" + s1.length());
        System.out.println("s2 : " + s2.length());
        System.out.println("s1 :" + s1.getBytes(StandardCharsets.UTF_8).length+"字节");
        System.out.println("s2 :" + s2.getBytes(StandardCharsets.UTF_8).length+"字节"); // 1354个字节
        for(int i=0;i<100;i++){
            String str = i % 2 ==0 ? s1 : s2; //交替发送s1 s2

            byte[] datas = str.getBytes(); //真实数据
            int length = datas.length; //真实数据的字节数

            byte[] bytes = new byte[4 + datas.length]; //声明数组 其中前4个byte用来存数据的长度 那么可表示4GB的数据
            //将长度存在前面的4个字节中
            bytes[0] = (byte) ((length >> 24) & 0xff);
            bytes[1] = (byte) ((length >> 16) & 0xff);
            bytes[2] = (byte) ((length >> 8) & 0xff);
            bytes[3] = (byte) ((length) & 0xff);
            //将真实数据copy到bytes中
            int t = 4;
            for (byte data : datas){
                bytes[t] = data;
                t++;
            }
            //发送带长度的数据
            outputStream.write(bytes);
            System.out.println("完成"+i+"次");
        }
        socket.close();
    }
}
