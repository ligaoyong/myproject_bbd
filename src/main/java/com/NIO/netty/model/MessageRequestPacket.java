package com.NIO.netty.model;

import lombok.Data;

/**
 * 消息请求对象
 */
@Data
public class MessageRequestPacket extends Packet{

    private String message;

    @Override
    public Byte getCommand() {
        return Command.MESSAGE_REQUEST;
    }
}
