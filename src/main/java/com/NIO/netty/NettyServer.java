package com.NIO.netty;

import com.NIO.netty.handler.ServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.AttributeKey;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

/**
 * netty 主要的处理逻辑在handle里面 其他的都是模板代码
 * 而多个handle可以形成一条handle链 注意执行顺序即可
 * ======读数据的handle与添加的顺序保持一致
 * ======写数据的handle与添加的顺序相反
 */
public class NettyServer {
    public static void main(String[] args) {
        //相当于线程组
        NioEventLoopGroup bossGroup = new NioEventLoopGroup(); //负责处理连接请求的线程组
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();//负责读写每个连接的线程组

        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap
                .group(bossGroup, workerGroup) //绑定现场模型
                .channel(NioServerSocketChannel.class) //指定为NIO模型
                .childHandler(new ChannelInitializer<NioSocketChannel>() { //具体的io逻辑 也是netty的难点所在
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        //添加通道处理器
                        /*ch.pipeline().addLast(new ChannelInboundHandlerAdapter() {
                            @Override
                            //读取客户端发过来的数据
                            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                //收到数据
                                ByteBuf byteBuf = (ByteBuf) msg;
                                System.out.println(LocalDateTime.now() + ": 服务端读到数据 -> " +
                                        byteBuf.toString(StandardCharsets.UTF_8));

                                // 回复数据到客户端
                                System.out.println(LocalDateTime.now() + ": 服务端写出数据");
                                byte[] bytes = "你好，欢迎关注我的微信公众号，《闪电侠的博客》!".getBytes(
                                        Charset.forName("utf-8"));
                                ByteBuf buffer = ctx.alloc().buffer();
                                buffer.writeBytes(bytes);
                                ctx.channel().writeAndFlush(buffer);
                            }
                        });*/
                        ch.pipeline().addLast(new ServerHandler());
                    }
                });
        //serverBootstrap.bind(8000);

        /**
         * attr()方法可以给服务端的 channel，也就是NioServerSocketChannel指定一些自定义属性，
         * 然后我们可以通过channel.attr()取出这个属性，比如，上面的代码我们指定我们服务端channel的一个serverName属性，
         * 属性值为nettyServer，其实说白了就是给NioServerSocketChannel维护一个map而已，通常情况下，我们也用不上这个方法。
         * 那么，当然，除了可以给服务端 channel NioServerSocketChannel指定一些自定义属性之外，我们还可以给每一条连接指定自定义属性
         */
        serverBootstrap.attr(AttributeKey.newInstance("serverName"), "nettyServer");

        /**
         * childAttr可以给每一条连接指定自定义属性，然后后续我们可以通过channel.attr()取出该属性。
         */
        serverBootstrap.childAttr(AttributeKey.newInstance("clientKey"), "clientValue");

        /**
         * handler()方法呢，可以和我们前面分析的childHandler()方法对应起来，
         * childHandler()用于指定处理新连接数据的读写处理逻辑，
         * handler()用于指定在服务端启动过程中的一些逻辑，通常情况下呢，我们用不着这个方法。
         */
        serverBootstrap.handler(new ChannelInitializer<NioServerSocketChannel>() {
            protected void initChannel(NioServerSocketChannel ch) {
                System.out.println("服务端启动中");
                //取出服务端channel的属性
                //ch.attr(AttributeKey.newInstance("serverName"));
            }
        });

        /**
         * childOption()可以给每条连接设置一些TCP底层相关的属性
         */
        serverBootstrap.childOption(ChannelOption.SO_KEEPALIVE, true);

        /**
         * 给服务端channel设置一些属性，最常见的就是so_backlog，如下设置
         * 表示系统用于临时存放已完成三次握手的请求的队列的最大长度，
         * 如果连接建立频繁，服务器处理创建新连接较慢，可以适当调大这个参数
         */
        serverBootstrap.option(ChannelOption.SO_BACKLOG, 1024);

        bind(serverBootstrap,8888);
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
