package com.NIO.netty.handler.handlerChain;

import com.NIO.netty.model.Packet;
import com.NIO.netty.model.PacketCodeC;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * MessageToByteEncoder 继承 ChannelOutboundHandlerAdapter
 * 这也是一个负责写逻辑的handler
 */
public class PacketEncoder extends MessageToByteEncoder<Packet> {
    @Override
    protected void encode(ChannelHandlerContext ctx, Packet msg, ByteBuf out) throws Exception {
        PacketCodeC.encode(out,msg);
        //不需要调用ctx.channel().writeAndFlush(xxx)????;
    }
}
