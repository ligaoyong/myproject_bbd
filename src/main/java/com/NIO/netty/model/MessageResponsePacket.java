package com.NIO.netty.model;

import lombok.Data;

@Data
public class MessageResponsePacket extends Packet{
    private String fromName;
    private String message;

    @Override
    public Byte getCommand() {
        return Command.MESSAGE_RESPONSE;
    }
}
