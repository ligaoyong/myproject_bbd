package com.netty.stick_package.nohandle_stick_package;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;

/**
 * 测试粘包与拆包
 *
 * @author ligaoyong@gogpay.cn
 * @date 2019/12/24 10:07
 */
public class TimeClientHandler extends ChannelInboundHandlerAdapter {

    private static final Logger logger = LoggerFactory.getLogger(TimeClientHandler.class);

    private int counter;

    private byte[] req;

    public TimeClientHandler() {
        req = ("QUERY TIME ORDER" + System.getProperty("line.separator")).getBytes();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ByteBuf message = null;
        for (int i = 0;i<100;i++){
            message = Unpooled.buffer(req.length);
            message.writeBytes(req);
            ctx.writeAndFlush(message);
        }
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buf = (ByteBuf) msg;
        byte[] bytes = new byte[buf.readableBytes()];
        buf.readBytes(bytes);
        String body = new String(bytes, StandardCharsets.UTF_8);
        System.out.println("Now is : "+body + " ; the counter is : " + ++counter);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.warn("Unexcepted exception from downstream : "+cause.getMessage());
        ctx.close();
    }
}
