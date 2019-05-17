package com.NIO.netty.model;

import io.netty.util.AttributeKey;

/**
 * 通道属性
 */
public interface Attributes {
    AttributeKey LOGIN = AttributeKey.newInstance("login");
}
