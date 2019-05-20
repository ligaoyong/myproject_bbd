package com.NIO.netty.handler.handlerChain;

import com.NIO.netty.model.MessageResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * 客户端 单聊 收到消息
 */
public class SimpleChatMessageResponseHandler extends SimpleChannelInboundHandler<MessageResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageResponsePacket msg) throws Exception {
        System.out.println(msg.getFromName()+" : "+msg.getMessage());
    }
}
