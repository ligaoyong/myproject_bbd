package com.NIO.netty.handler.handlerChain;

import com.NIO.netty.model.MessageRequestPacket;
import com.NIO.netty.model.MessageResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.time.LocalDateTime;

/**
 * 消息请求处理器
 */
public class MessageRequestHandler extends SimpleChannelInboundHandler<MessageRequestPacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageRequestPacket msg) throws Exception {
        ctx.channel().writeAndFlush(receiveMessage(msg));
    }

    private MessageResponsePacket receiveMessage(MessageRequestPacket msg){
        System.out.println(LocalDateTime.now() + ": 收到客户端消息: " + msg.getMessage());
        MessageResponsePacket messageResponsePacket = new MessageResponsePacket();
        messageResponsePacket.setMessage("服务端回复【" + msg.getMessage() + "】");
        return messageResponsePacket;
    }
}
