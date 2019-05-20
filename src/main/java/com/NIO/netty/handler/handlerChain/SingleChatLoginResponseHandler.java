package com.NIO.netty.handler.handlerChain;

import com.NIO.netty.model.Attributes;
import com.NIO.netty.model.LoginRequestPacket;
import com.NIO.netty.model.LoginResponsePacket;
import com.NIO.netty.util.Session;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * 客户端 单聊登录响应处理器
 */
public class SingleChatLoginResponseHandler extends SimpleChannelInboundHandler<LoginResponsePacket> {

//    /**
//     * 连接成功时登录
//     * @param ctx
//     * @throws Exception
//     */
//    @Override
//    public void channelActive(ChannelHandlerContext ctx) throws Exception {
//        LoginRequestPacket loginRequestPacket = new LoginRequestPacket();
//        loginRequestPacket.setUsername("李四");
//        ctx.channel().writeAndFlush(loginRequestPacket);
//    }

    @SuppressWarnings("all")
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginResponsePacket msg) throws Exception {
        if (msg.getSuccess()){
            System.out.println("登录成功，userId："+msg.getUserId());
            ctx.channel().attr(Attributes.SESSION).set(new Session(msg.getUserId(),""));
        }else {
            System.out.println("登录失败");
        }
    }
}
