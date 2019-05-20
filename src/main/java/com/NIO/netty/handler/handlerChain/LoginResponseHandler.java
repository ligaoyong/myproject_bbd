package com.NIO.netty.handler.handlerChain;

import com.NIO.netty.model.LoginRequestPacket;
import com.NIO.netty.model.LoginResponsePacket;
import com.NIO.netty.model.PacketCodeC;
import com.NIO.netty.util.LoginUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.nio.charset.Charset;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * 客户端登陆响应处理器
 */
public class LoginResponseHandler extends SimpleChannelInboundHandler<LoginResponsePacket> {

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
        ctx.channel().writeAndFlush(byteBuf); //异步的
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginResponsePacket msg) throws Exception {
        if (msg.getSuccess()) {
            System.out.println(LocalDateTime.now() + ": 客户端登录成功");
            //标记该通道登陆成功
            LoginUtil.markAsLogin(ctx.channel());
        } else {
            System.out.println(LocalDateTime.now() + ": 客户端登录失败，原因：" + msg.getReason());
        }
    }
}
