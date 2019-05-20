package com.NIO.netty.handler.handlerChain;

import com.NIO.netty.model.LoginRequestPacket;
import com.NIO.netty.model.LoginResponsePacket;
import com.NIO.netty.util.LoginUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * 登陆请求处理器 根据泛型自动调用handler处理
 */
public class LoginRequestHandler extends SimpleChannelInboundHandler<LoginRequestPacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginRequestPacket msg) throws Exception {
        //服务端标记channel已经登录
        LoginUtil.markAsLogin(ctx.channel());
        ctx.channel().writeAndFlush(login(msg)); //如果有下一个处理器 则默认调用 比如编码
    }

    private LoginResponsePacket login(LoginRequestPacket msg){
        LoginResponsePacket loginResponsePacket = new LoginResponsePacket();
        loginResponsePacket.setSuccess(true);
        return loginResponsePacket;
    }
}
