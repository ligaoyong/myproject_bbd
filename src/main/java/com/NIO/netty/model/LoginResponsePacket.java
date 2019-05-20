package com.NIO.netty.model;

import lombok.Data;

@Data
public class LoginResponsePacket extends Packet {

    private Boolean success;
    private String reason;
    private String userId;

    @Override
    public Byte getCommand() {
        return Command.LOGIN_RESPONSE;
    }
}
