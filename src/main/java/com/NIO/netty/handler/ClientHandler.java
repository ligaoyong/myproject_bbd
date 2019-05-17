package com.NIO.netty.handler;

import com.NIO.netty.util.LoginRequestPacket;
import com.NIO.netty.util.LoginResponsePacket;
import com.NIO.netty.util.Packet;
import com.NIO.netty.util.PacketCodeC;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * 客户端处理器
 */
public class ClientHandler extends ChannelInboundHandlerAdapter {
    /**
     * 我们实现在客户端连接上服务端之后，立即登录。
     * 在连接上服务端之后，Netty 会回调到 ClientHandler 的 channelActive() 方法，
     * 我们在这个方法体里面编写相应的逻辑
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(LocalDateTime.now() + ": 客户端开始登录");

        // 创建登录对象
        LoginRequestPacket loginRequestPacket = new LoginRequestPacket();
        loginRequestPacket.setUserId(UUID.randomUUID().toString());
        loginRequestPacket.setUsername("flash");
        loginRequestPacket.setPassword("pwd");

        //序列化
        ByteBuf byteBuf = PacketCodeC.encode(loginRequestPacket);

        //发送数据
        ctx.channel().writeAndFlush(byteBuf);
    }

    /**
     * 读取服务端发送的响应信息
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf) msg;

        Packet packet = PacketCodeC.decode(byteBuf);

        if (packet instanceof LoginResponsePacket) {
            LoginResponsePacket loginResponsePacket = (LoginResponsePacket) packet;

            if (loginResponsePacket.getSuccess()) {
                System.out.println(LocalDateTime.now() + ": 客户端登录成功");
            } else {
                System.out.println(LocalDateTime.now() + ": 客户端登录失败，原因：" + loginResponsePacket.getReason());
            }
        }
    }
}
