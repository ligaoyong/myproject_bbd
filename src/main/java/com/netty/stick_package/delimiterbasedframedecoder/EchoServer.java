package com.netty.stick_package.delimiterbasedframedecoder;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

/**
 * 时间服务器 DelimiterBasedFrameDecoder解决粘包与拆包
 * 分隔符的粘包处理方案
 *
 * @author ligaoyong@gogpay.cn
 * @date 2019/12/24 9:38
 */
public class EchoServer {
    public static void main(String[] args) throws InterruptedException {
        int port = 8080;
        new EchoServer().bind(port);
    }
    public void bind(int port) throws InterruptedException {
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap
                    .group(bossGroup, workerGroup)
                    //创建一个NioServerSocketChannel 服务器的通道
                    .channel(NioServerSocketChannel.class)
                    //针对NioServerSocketChannel的设置
                    .option(ChannelOption.SO_BACKLOG, 1024)
                    //子通道SocketChannel的处理器
                    .childHandler(new ChildChannelHandler())
                    //针对子通道SocketChannel的设置
                    .childOption(ChannelOption.SO_BACKLOG, 1024);
            //绑定端口，同步等待成功
            ChannelFuture f = bootstrap.bind(port).sync();
            //等待服务端监听端口关闭
            f.channel().closeFuture().sync();
        }finally {
            //优雅退出  释放线程池资源
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    /**
     * 服务端监听端口的通道类型是 NioServerSocketChannel
     * 而客户端连接成功后的通道类型是SocketChannel
     */
    private class ChildChannelHandler extends ChannelInitializer<SocketChannel> {
        //每一个客户端连接都会创建一个SocketChannel
        @Override
        protected void initChannel(SocketChannel ch) throws Exception {
            //添加基于分隔符的粘包处理方案
            ByteBuf delimiter = Unpooled.copiedBuffer("$_".getBytes());
            ch.pipeline().addLast(new DelimiterBasedFrameDecoder(1024,delimiter));
            ch.pipeline().addLast(new StringDecoder());
            ch.pipeline().addLast(new EchoServerHandler());
        }
    }
}
