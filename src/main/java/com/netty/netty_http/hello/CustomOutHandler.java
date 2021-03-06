package com.netty.netty_http.hello;

import io.netty.buffer.*;
import io.netty.channel.*;
import io.netty.handler.codec.http.*;

import java.nio.charset.StandardCharsets;

import static io.netty.handler.codec.http.HttpHeaderNames.CONNECTION;
import static io.netty.handler.codec.http.HttpHeaderNames.CONTENT_LENGTH;
import static io.netty.handler.codec.http.HttpHeaderNames.CONTENT_TYPE;
import static io.netty.handler.codec.http.HttpHeaderValues.CLOSE;
import static io.netty.handler.codec.http.HttpHeaderValues.TEXT_PLAIN;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;

/**
 * 处理String消息的写回
 *
 * @author ligaoyong@gogpay.cn
 * @date 2019/12/13 17:21
 */
public class CustomOutHandler extends ChannelOutboundHandlerAdapter {
    /**
     * 将string消息转换为HttpResponse  然后由HttpServerCodec中的HttpObjectEncoder处理
     * HttpObjectEncoder默认处理HttpResponse的消息
     *
     * @param ctx
     * @param msg
     * @param promise
     */
    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise){
        if (msg instanceof String) {
            FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, OK,
                    Unpooled.wrappedBuffer(((String) msg).getBytes(StandardCharsets.UTF_8)));
            response.headers()
                    .set("Content-Type", "text/html;charset=utf-8")
                    .setInt(CONTENT_LENGTH, response.content().readableBytes());

            // Tell the client we're going to close the connection.
            response.headers().set(CONNECTION, CLOSE);
            ChannelFuture f = ctx.write(response);
            f.addListener(ChannelFutureListener.CLOSE);

            //尽量不要在Out中使用ctx.channel().write(response) 避免死循环的出现
            //ctx.channel().write(response);
        }
    }
}
