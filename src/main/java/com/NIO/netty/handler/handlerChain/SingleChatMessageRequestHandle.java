package com.NIO.netty.handler.handlerChain;

import com.NIO.netty.model.Attributes;
import com.NIO.netty.model.MessageRequestPacket;
import com.NIO.netty.model.MessageResponsePacket;
import com.NIO.netty.util.SessionUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * 单聊 消息请求处理器 : 接收消息 转发消息
 */
public class SingleChatMessageRequestHandle extends SimpleChannelInboundHandler<MessageRequestPacket> {
    @SuppressWarnings("all")
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageRequestPacket msg) throws Exception {
        MessageResponsePacket responsePacket = new MessageResponsePacket();
        String toUserId = msg.getToUserId();
        Channel toChannel = SessionUtil.getChannel(toUserId);
        if (toChannel == null || toChannel.attr(Attributes.SESSION).get() == null){ //目标不在线
            System.out.println("目标不在线");
        }else {
            responsePacket.setFromName(SessionUtil.getSession(ctx.channel()).getUserName());
            responsePacket.setMessage(msg.getMessage());
            toChannel.writeAndFlush(responsePacket);
        }

    }
}
