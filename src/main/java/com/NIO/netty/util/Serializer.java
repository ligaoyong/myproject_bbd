package com.NIO.netty.util;

/**
 * 序列化器
 */
public interface Serializer {
    /**
     * 序列化算法
     */
    byte getSerializerAlgorithm();

    /**
     * java 对象转换成二进制    序列化
     */
    byte[] serialize(Object object);

    /**
     * 二进制转换成 java 对象  反序列化
     */
    <T> T deserialize(Class<T> clazz, byte[] bytes);

    /**
     * 默认的序列化器
     */
    Serializer DEFAULT = new JSONSerializer();
}
