package com.NIO.netty.handler.handlerChain;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.Charset;

/**
 * 测试拆包、粘包现象
 */
public class FirstClientHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        //测试拆包、粘包
        for (int i = 0; i < 1000; i++) {
            byte[] bytes = "你好，欢迎关注我的微信公众号，《闪电侠的博客》!".getBytes(Charset.forName("utf-8"));
            //byte[] bytes = "你好，欢迎关注我的微信公众号，《闪电侠的博客》!\n".getBytes(Charset.forName("utf-8"));
            //byte[] bytes = "你好，欢迎关注我的微信公众号，《闪电侠的博客》!||".getBytes(Charset.forName("utf-8"));
            ByteBuf buffer = ctx.alloc().buffer();
            buffer.writeBytes(bytes);
            ctx.channel().writeAndFlush(buffer);
        }
    }
}
