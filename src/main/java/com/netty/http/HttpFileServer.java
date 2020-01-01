package com.netty.http;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * HTTP文件服务器
 *
 * @author ligaoyong@gogpay.cn
 * @date 2019/12/24 9:38
 */
public class HttpFileServer {

    private static final String DEFAULT_URL = "/";

    public static void main(String[] args) throws InterruptedException {
        int port = 8080;
        new HttpFileServer().run(port,DEFAULT_URL);
    }

    public void run(final int port,final String url) throws InterruptedException {
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap
                    .group(bossGroup, workerGroup)
                    //创建一个NioServerSocketChannel 服务器的通道
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast("http-decoder", new HttpRequestDecoder());
                            //http对象聚合 将多个消息转换为单一的FullHttpRequest或者FullHttpResponse，
                            //原因是Http解码器zai在每个HTTP消息中会生成多个消息对象：HttpRequest/HttpResponse/HttpContent/LastHttpContent
                            ch.pipeline().addLast("http-aggregator", new HttpObjectAggregator(65535));
                            ch.pipeline().addLast("http-encoder", new HttpResponseEncoder());
                            //支持异步发送大的码流
                            ch.pipeline().addLast("http-chunked", new ChunkedWriteHandler());
                            //业务处理
                            ch.pipeline().addLast("fileServerHandler", new HttpFileServerHandler(url));
                        }
                    })
                    ;
            //绑定端口，同步等待成功
            ChannelFuture f = bootstrap.bind("0.0.0.0",port).sync();
            System.out.println("HTTP 文件目录服务器启动，地址："+"http://localhost:"+port+url);
            //等待服务端监听端口关闭
            f.channel().closeFuture().sync();
        }finally {
            //优雅退出  释放线程池资源
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

}
