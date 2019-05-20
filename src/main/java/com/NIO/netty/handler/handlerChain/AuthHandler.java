package com.NIO.netty.handler.handlerChain;

import com.NIO.netty.util.LoginUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * 身份认证处理器 类似于拦截器 可拔插
 */
public class AuthHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (!LoginUtil.hasLogin(ctx.channel())){ //如果没有登录
            ctx.channel().close();
        }else {
            //如果已经登录 则以后不用每次都调用AuthHandler 可以移除这个channel上的AuthHandler
            ctx.channel().pipeline().remove(this);
            super.channelRead(ctx, msg);
        }
    }

    /**
     * 移除handler时会调用这个方法
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        if (LoginUtil.hasLogin(ctx.channel())) {
            System.out.println("当前连接登录验证完毕，无需再次验证, AuthHandler 被移除");
        } else {
            System.out.println("无登录验证，强制关闭连接!");
        }
    }
}
