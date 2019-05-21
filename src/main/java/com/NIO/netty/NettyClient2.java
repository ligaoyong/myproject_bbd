package com.NIO.netty;

import com.NIO.netty.handler.handlerChain.*;
import com.NIO.netty.model.LoginRequestPacket;
import com.NIO.netty.model.MessageRequestPacket;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.AttributeKey;

import java.time.LocalDateTime;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

/**
 * 客户端2 ： 李四
 */
public class NettyClient2 {
    public static void main(String[] args) {
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(workerGroup) //指定线程模型
                .channel(NioSocketChannel.class)//指定io模型为 客户端Nio模型
                .handler(new ChannelInitializer<SocketChannel>() { //io逻辑
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
//                        Attribute<Object> clientName = ch.attr(AttributeKey.newInstance("clientName"));
//                        System.out.println(clientName.get());
                        /*ch.pipeline().addLast(new ChannelInboundHandlerAdapter() {
                            @Override
                            //这个方法会在客户端连接建立成功之后被调用
                            public void channelActive(ChannelHandlerContext ctx) throws Exception {
                                System.out.println(LocalDateTime.now() + ": 客户端写出数据");

                                ByteBuf buffer = ctx.alloc().buffer();
                                byte[] bytes = "你好，闪电侠!".getBytes(StandardCharsets.UTF_8);
                                buffer.writeBytes(bytes);
                                //向服务端写数据
                                ctx.channel().writeAndFlush(buffer);
                            }

                            @Override
                            //读取服务端发过来的数据
                            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                ByteBuf byteBuf = (ByteBuf) msg;
                                System.out.println(LocalDateTime.now() + ": 客户端读到数据 -> " +
                                        byteBuf.toString(Charset.forName("utf-8")));
                            }
                        });*/
                        //ch.pipeline().addLast(new ClientHandler());

//                        //in
//                        ch.pipeline().addLast(new PacketDecoder());
//                        ch.pipeline().addLast(new LoginResponseHandler());
//                        ch.pipeline().addLast(new MessageResponseHandler());
//                        //out
//                        ch.pipeline().addLast(new PacketEncoder());

                        //测试拆包、粘包现象
                        //ch.pipeline().addLast(new FirstClientHandler());

                        /*****************单聊******************/
                        ch.pipeline().addLast(new PacketDecoder());
                        ch.pipeline().addLast(new SingleChatLoginResponseHandler());
                        ch.pipeline().addLast(new SingleChatMessageResponseHandler());
                        ch.pipeline().addLast(new PacketEncoder());
                    }
                });

        /**
         * attr() 方法可以给客户端 Channel，也就是NioSocketChannel绑定自定义属性，
         * 然后我们可以通过channel.attr()取出这个属性
         * 后续在这个 NioSocketChannel 通过参数传来传去的时候，就可以通过他来取出设置的属性，非常方便
          */
        bootstrap.attr(AttributeKey.newInstance("clientName"), "nettyClient");

        /**
         * option() 方法可以给连接设置一些 TCP 底层相关的属性，比如上面，我们设置了三种 TCP 属性，其中
         * ChannelOption.CONNECT_TIMEOUT_MILLIS 表示连接的超时时间，超过这个时间还是建立不上的话则代表连接失败
         * ChannelOption.SO_KEEPALIVE 表示是否开启 TCP 底层心跳机制，true 为开启
         * ChannelOption.TCP_NODELAY 表示是否开始 Nagle 算法，true 表示关闭，
         *      false 表示开启，通俗地说，如果要求高实时性，有数据发送时就马上发送，
         *      就设置为 true 关闭，如果需要减少发送次数减少网络交互，就设置为 false 开启
         */
        bootstrap.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .option(ChannelOption.TCP_NODELAY, true);
        //建立连接
        connect(bootstrap,"0.0.0.0",8888,5);
    }

    //带重试的连接功能
    private static void connect(Bootstrap bootstrap,String host,int port,int retry){
        bootstrap.connect(host, port).addListener(future -> {
            if (future.isSuccess()) {
                System.out.println("连接成功");
                //连接成功或可以获取Channel 所有的客户端数据发送都需要用这个Channel
                Channel channel = ((ChannelFuture) future).channel();
                // 连接成功之后，启动控制台线程
                startConsoleThread(channel);
            } else {
                System.out.println("连接失败");
                // 第几次重连
                int order = (5 - retry) + 1;
                // 本次重连的间隔
                int delay = 1 << order;
                System.err.println(LocalDateTime.now() + ": 连接失败，第" + order + "次重连……");
                /**
                 * 我们定时任务是调用 bootstrap.config().group().schedule(),
                 * 其中 bootstrap.config() 这个方法返回的是 BootstrapConfig，
                 * 他是对 Bootstrap 配置参数的抽象，然后 bootstrap.config().group()
                 * 返回的就是我们在一开始的时候配置的线程模型 workerGroup，
                 * 调 workerGroup 的 schedule 方法即可实现定时任务逻辑。
                 */
                bootstrap.config().group().schedule(() -> connect(bootstrap, host, port, retry - 1), delay, TimeUnit
                        .SECONDS);
            }
        });
    }

    private static void startConsoleThread(Channel channel) {
        new Thread(() -> {
            String userName = "李四";
            LoginRequestPacket loginRequestPacket = new LoginRequestPacket();
            loginRequestPacket.setUsername(userName);
            channel.writeAndFlush(loginRequestPacket);

            while (!Thread.interrupted()) {
                //if (LoginUtil.hasLogin(channel)) { 登录状态由服务端维护
                //System.out.println("输入消息发送: ");
                Scanner scc = new Scanner(System.in);
                String line = scc.nextLine();

                MessageRequestPacket packet = new MessageRequestPacket();
                packet.setToUserId("zhangsan"); //发消息
                packet.setMessage(line);

//                    ByteBuf byteBuf = PacketCodeC.encode(packet);
//                    channel.writeAndFlush(byteBuf);
                channel.writeAndFlush(packet);
                //}
            }
        }).start();
    }
}
