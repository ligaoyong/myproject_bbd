package com.netty.stick_package.delimiterbasedframedecoder;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

/**
 * DelimiterBasedFrameDecoder解决粘包与拆包
 * 分隔符的粘包处理方案
 *
 * @author ligaoyong@gogpay.cn
 * @date 2019/12/24 10:03
 */
public class EchoClient {
    public static void main(String[] args) throws InterruptedException {
        int port = 8080;
        new EchoClient().connect(port,"127.0.0.1");
    }

    public void connect(int port,String host) throws InterruptedException {
        //客户端只需要一个处理读写的线程组即可
        NioEventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap
                    .group(group)
                    .channel(NioSocketChannel.class)
                    //针对NioSockketChannel的设置
                    .option(ChannelOption.TCP_NODELAY,true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            //添加基于分隔符的粘包处理方案
                            ByteBuf delimiter = Unpooled.copiedBuffer("$_".getBytes());
                            ch.pipeline().addLast(new DelimiterBasedFrameDecoder(1024,delimiter));
                            ch.pipeline().addLast(new StringDecoder());
                            ch.pipeline().addLast(new EchoClientHandler());
                        }
                    });
            //发起连接
            ChannelFuture f = bootstrap.connect(host, port).sync();
            //等待客户端通道关闭
            f.channel().closeFuture().sync();
        }finally {
            group.shutdownGracefully();
        }
    }
}
