package com.NIO.netty.util;

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
