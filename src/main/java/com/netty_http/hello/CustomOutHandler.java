package com.netty_http.hello;

import io.netty.buffer.*;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.HttpMessage;

import java.nio.ByteBuffer;

/**
 * 处理String消息的写回
 *
 * @author ligaoyong@gogpay.cn
 * @date 2019/12/13 17:21
 */
public class CustomOutHandler extends ChannelOutboundHandlerAdapter {
    /**
     * 将string消息传唤为ByteBuff或者http相关的 不然HttpServerCodec中的HttpObjectEncoder不会处理
     * HttpObjectEncoder默认不处理字符串消息
     * @param ctx
     * @param msg
     * @param promise
     * @throws Exception
     */
    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        if (msg instanceof String){
            //线程本地的缓冲
            ByteBuf byteBuf = ByteBufUtil.threadLocalDirectBuffer();
            byteBuf.writeBytes(((String) msg).getBytes());
            ctx.writeAndFlush(byteBuf).addListener((future) ->ctx.close());
        }
    }
}
