package com.netty.stick_package.nohandle_stick_package;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;

/**
 * 测试粘包与拆包
 *
 * @author ligaoyong@gogpay.cn
 * @date 2019/12/24 9:51
 */
public class TimeServerHandler extends ChannelInboundHandlerAdapter {

    private int counter;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buf = (ByteBuf) msg;
        byte[] req = new byte[buf.readableBytes()];
        buf.readBytes(req);
        String body = new String(req, StandardCharsets.UTF_8).substring(0,req.length - System.getProperty("line.separator").length());
        System.out.println("The time server receive order : "+body + " ; the counter is : " + ++counter);

        String currentTime = "QUERY TIME ORDER".equalsIgnoreCase(body) ? LocalDate.now().toString() : "BAD ORDER";
        currentTime = currentTime + System.getProperty("line.separator");
        ByteBuf resp = Unpooled.copiedBuffer(currentTime.getBytes());
        ctx.write(resp);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
