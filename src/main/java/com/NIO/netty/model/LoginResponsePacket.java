package com.NIO.netty.model;

import lombok.Data;

@Data
public class LoginResponsePacket extends Packet {

    private Boolean success;
    private String reason;

    @Override
    public Byte getCommand() {
        return Command.LOGIN_RESPONSE;
    }
}
