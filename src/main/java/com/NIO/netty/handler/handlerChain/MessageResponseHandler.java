package com.NIO.netty.handler.handlerChain;

import com.NIO.netty.model.MessageResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.time.LocalDateTime;

/**
 * 客户端 消息响应处理器
 */
public class MessageResponseHandler extends SimpleChannelInboundHandler<MessageResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageResponsePacket msg) throws Exception {
        System.out.println(LocalDateTime.now() + ": 收到服务端的消息: " + msg.getMessage());
    }
}
