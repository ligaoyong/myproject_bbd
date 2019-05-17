package com.NIO.netty.handler;

import com.NIO.netty.model.*;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.time.LocalDateTime;

/**
 * 服务端处理器
 */
public class ServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf requestByteBuf = (ByteBuf) msg;

        // 解码
        Packet packet = PacketCodeC.decode(requestByteBuf);

        // 判断是否是登录请求数据包
        if (packet instanceof LoginRequestPacket) {
            LoginRequestPacket loginRequestPacket = (LoginRequestPacket) packet;
            //响应包
            LoginResponsePacket loginResponsePacket = new LoginResponsePacket();
            loginResponsePacket.setVersion(packet.getVersion());
            if (valid(loginRequestPacket)) {
                loginResponsePacket.setSuccess(true);
            } else {
                loginResponsePacket.setReason("账号密码校验失败");
                loginResponsePacket.setSuccess(false);
            }
            //返回给客户端
            ByteBuf byteBuf = PacketCodeC.encode(loginResponsePacket);
            ctx.channel().writeAndFlush(byteBuf);
        }else if (packet instanceof MessageRequestPacket) {// 处理消息请求
            MessageRequestPacket messageRequestPacket = ((MessageRequestPacket) packet);
            System.out.println(LocalDateTime.now() + ": 收到客户端消息: " + messageRequestPacket.getMessage());

            MessageResponsePacket messageResponsePacket = new MessageResponsePacket();
            messageResponsePacket.setMessage("服务端回复【" + messageRequestPacket.getMessage() + "】");
            ByteBuf responseByteBuf = PacketCodeC.encode(messageResponsePacket);
            ctx.channel().writeAndFlush(responseByteBuf);
        }
    }

    private boolean valid(LoginRequestPacket loginRequestPacket) {
        return true;
    }
}
