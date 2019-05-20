package com.NIO.netty.handler.handlerChain;

import com.NIO.netty.model.LoginRequestPacket;
import com.NIO.netty.model.LoginResponsePacket;
import com.NIO.netty.util.Session;
import com.NIO.netty.util.SessionUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.UUID;

/**
 * 单聊  服务端登录请求处理器
 */
public class SingleChatLoginRequestHandler extends SimpleChannelInboundHandler<LoginRequestPacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginRequestPacket msg) throws Exception {
        LoginResponsePacket loginResponsePacket = new LoginResponsePacket();
        loginResponsePacket.setSuccess(true);
        String userId = UUID.randomUUID().toString().substring(0, 6); //一般来说 用户的userId是固定的
        if (msg.getUsername().equals("张三")){
            userId = "zhangsan";
        }
        if (msg.getUsername().equals("李四")){
            userId = "lisi";
        }
        loginResponsePacket.setUserId(userId);
        //绑定会话
        SessionUtil.bindSession(new Session(userId,msg.getUsername()),ctx.channel());
        System.out.println(msg.getUsername()+"登录成功");
        ctx.channel().writeAndFlush(loginResponsePacket);
    }
}
