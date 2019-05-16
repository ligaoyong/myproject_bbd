package com.NIO;

import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Set;

/**
 * 多路复用器/选择器
 */
public class Selector1 {

    /**
     * 服务端
     *
     * @throws IOException
     */
    @Test
    public void selector() throws IOException {
        Selector selector = Selector.open();
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false); //配置为非阻塞通道
        serverSocketChannel.bind(new InetSocketAddress(8888));

        //将通道绑定到selector上 注册链接事件
        SelectionKey selectionKey = serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        while (true) {
            //有就绪事件发生的通道数
            int select = selector.select();
            if (select == 0)
                continue;
            /*************处理就绪事件*****************/
            //就绪事件发生的通道对应的 SelectionKey集合
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            //遍历集合处理每个通道
            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while (iterator.hasNext()) {
                try {
                    SelectionKey key = iterator.next();
                    //处理链接事件事件--通常的B/S架构服务端、客户端都无需处理连接时间 默认处理了
                /*if (key.isConnectable()){
                    System.out.println("链接事件发生，在ServerSocketChannel注册accept事件");
                    //只有serverSocketChannel上才会发生链接事件 所以直接抢转
                    ServerSocketChannel channel = (ServerSocketChannel)key.channel();
                    //在该通道上注册Accept事件
                    channel.register(selector, SelectionKey.OP_ACCEPT);
                }*/
                    //服务端通常需要处理accept事件 从而获取与客户端交互的SocketChannel，并注册读事件
                    if (key.isAcceptable()) {
                        System.out.println("accept事件发生，" +
                                "调用ServerSocketChannel的accept()得到socketChannel，在该通道上注册读写事件");
                        //只有serverSocketChannel上才会发生链接事件 所以直接抢转
                        ServerSocketChannel serverSocketChannel1 = (ServerSocketChannel) key.channel();
                        SocketChannel socketChannel = serverSocketChannel1.accept();
                        socketChannel.configureBlocking(false);
                        //在socketChannel上注册读事件
                        socketChannel.register(selector, SelectionKey.OP_READ);
                    }
                    //处理读事件
                    if (key.isReadable()) {
                        //只有SocketChannel类型的通道上有读写事件 所以强制转型
                        SocketChannel socketChannel = (SocketChannel) key.channel();
                        //读取数据
                        ByteBuffer byteBuffer = ByteBuffer.allocate(100);
                        ByteBuffer allocate = ByteBuffer.allocate(1000);
                        int readed = socketChannel.read(byteBuffer);
                        while (readed > 0) { //读文件的时候可以是0、-1  但是读socket必须要大于0才有效 因为无法区分socket通道的末端
                            byteBuffer.flip();
                            byte[] data = new byte[readed];
                            byteBuffer.get(data);
                            System.out.println(new String(data, StandardCharsets.UTF_8));
                            //写回数据给客户端
                            allocate.put(data);
                            allocate.flip();
                            socketChannel.write(allocate);
                            allocate.clear();

                            byteBuffer.clear();
                            readed = socketChannel.read(byteBuffer);
                        }
                        //需要为该通道注册读事件 不然该通道只能读一次
                        socketChannel.register(selector, SelectionKey.OP_READ);
                    }

                    //一般情况下 服务端不用管写事件
                /*if (key.isWritable()){
                    //只有SocketChannel类型的通道上有读写事件 所以强制转型
                    SocketChannel socketChannel = (SocketChannel)key.channel();
                    //写入数据 本例转过来什么 写回什么
                    ByteBuffer byteBuffer = ByteBuffer.allocate(100);
                    int readed = socketChannel.read(byteBuffer);
                    while (readed != -1){
                        byteBuffer.flip();
                        socketChannel.write(byteBuffer); //写回缓冲区内容
                        byteBuffer.clear();
                        readed = socketChannel.read(byteBuffer);
                    }
                }*/
                    //从集合中移除当前key 不然下次会重复
                    iterator.remove();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Test
    public void client1() throws IOException {
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.connect(new InetSocketAddress(8888));
        ByteBuffer byteBuffer = ByteBuffer.allocate(1000);

        //向服务端发送数据
        byteBuffer.put("hello server, i am client".getBytes(StandardCharsets.UTF_8));
        byteBuffer.flip();
        socketChannel.write(byteBuffer);
        byteBuffer.clear();

        //从服务端读取数据
        int read = socketChannel.read(byteBuffer);
        while (read > 0) {
            byteBuffer.flip();
            System.out.println(new String(byteBuffer.array(), StandardCharsets.UTF_8));
            byteBuffer.clear();
            read = socketChannel.read(byteBuffer);
        }
        socketChannel.close();
    }
}
