package com.NIO.netty.model;

import lombok.Data;

@Data
/**
 * 抽象数据包
 */
public abstract class Packet {
    /**
     * 协议版本
     */
    private Byte version = 1;

    /**
     * 指令
     * @return
     */
    public abstract Byte getCommand();
}
