package com.NIO.netty.handler.handlerChain;

import com.NIO.netty.model.PacketCodeC;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * 包解码器Handler ByteToMessageDecoder是一个handler  以后收到消息就不用每次都手动解码
 */
public class PacketDecoder extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        out.add(PacketCodeC.decode(in));
    }
}
