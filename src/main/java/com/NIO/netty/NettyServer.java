package com.NIO.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class NettyServer {
    public static void main(String[] args) {
        //相当于线程组
        NioEventLoopGroup bossGroup = new NioEventLoopGroup(); //负责处理连接请求的现场组
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();//负责读写每个连接的现场组

        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap
                .group(bossGroup, workerGroup) //绑定现场模型
                .channel(NioServerSocketChannel.class) //指定为NIO模型
                .childHandler(new ChannelInitializer<NioSocketChannel>() { //具体的io逻辑
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {

                    }
                });
        //serverBootstrap.bind(8000);
        bind(serverBootstrap,8888);

        /**
         * handler()方法呢，可以和我们前面分析的childHandler()方法对应起来，
         * childHandler()用于指定处理新连接数据的读写处理逻辑，
         * handler()用于指定在服务端启动过程中的一些逻辑，通常情况下呢，我们用不着这个方法。
         */
        /*serverBootstrap.handler(new ChannelInitializer<NioServerSocketChannel>() {
            protected void initChannel(NioServerSocketChannel ch) {
                System.out.println("服务端启动中");
            }
        });*/
    }

    /**
     * serverBootstrap.bind(8000);这个方法呢，它是一个异步的方法，调用之后是立即返回的，他的返回值是一个ChannelFuture，
     * 我们可以给这个ChannelFuture添加一个监听器GenericFutureListener，
     * 然后我们在GenericFutureListener的operationComplete方法里面，我们可以监听端口是否绑定成功
     * @param serverBootstrap
     * @param port
     */
    private static void bind(final ServerBootstrap serverBootstrap, final int port) {
        //bind()方法返回ChannelFuture，而ChannelFuture可以添加监听器
        serverBootstrap.bind(port).addListener(future -> {
            if (future.isSuccess()){
                System.out.println("端口绑定成功："+port);
                return;
            }else
                System.out.println("端口绑定失败："+port);
                bind(serverBootstrap, port+1);
        });
    }
}
