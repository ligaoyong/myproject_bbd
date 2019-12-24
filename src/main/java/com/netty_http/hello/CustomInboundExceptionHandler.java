package com.netty_http.hello;

import io.netty.channel.*;

/**
 * 全局异常捕获
 *
 * @author ligaoyong@gogpay.cn
 * @date 2019/12/13 16:29
 */
public class CustomInboundExceptionHandler extends ChannelInboundHandlerAdapter {
    /**
     * 捕捉到异常 直接写回  作为最后一个Inbound
     * @param ctx
     * @param cause
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        ctx.writeAndFlush("异常：" + cause.getMessage()).addListener(ChannelFutureListener.CLOSE);
        /*ctx.channel().writeAndFlush("我是通过ctx.channel写回的，消息会从handler链表从尾部向前查询Out类型的handler进行处理")
                .addListener(ChannelFutureListener.CLOSE);*/
    }
}
