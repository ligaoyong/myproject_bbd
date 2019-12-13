package com.netty_http.hello;

import io.netty.channel.*;

/**
 * 全局异常捕获
 *
 * @author ligaoyong@gogpay.cn
 * @date 2019/12/13 16:29
 */
public class CustomDuplexExceptionHandler extends ChannelInboundHandlerAdapter {
    /**
     * 捕捉到异常 直接写回  作为最后一个Inbound
     * @param ctx
     * @param cause
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        ctx.writeAndFlush("异常：" + cause.getMessage());
    }
}
