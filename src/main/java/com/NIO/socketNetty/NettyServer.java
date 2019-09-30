package com.NIO.socketNetty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;

/**
 * netty 服务端
 *
 * @author ligaoyong@gogpay.cn
 * @date 2019/9/30 16:34
 */
@Slf4j
public class NettyServer {

    private static Logger LOGGER = LoggerFactory.getLogger(NettyServer.class);

    public static void main(String[] args) {
        /**
         * 服务端需要2个线程组  boss处理客户端连接  work进行客服端连接之后的处理
         */
        EventLoopGroup boss = new NioEventLoopGroup();
        EventLoopGroup work = new NioEventLoopGroup();

        // WS服务运行端口
        int port = 8888;

        try {
            //服务器 配置
            ServerBootstrap sbs = new ServerBootstrap().group(boss, work)
                    .channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .localAddress(new InetSocketAddress(port))
                    .childHandler(new Handler())
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);

            //绑定端口  开启事件驱动
            ChannelFuture future = sbs.bind(port).sync();

            if (future.isSuccess()) {
                LOGGER.info("---------->WebSocket服务启动成功，端口：{}<----------", port);
            }

            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            LOGGER.error("WebSocket服务器启动异常", e);
            Thread.currentThread().interrupt();
        } finally {
            boss.shutdownGracefully().syncUninterruptibly();
            work.shutdownGracefully().syncUninterruptibly();
        }
    }
}
