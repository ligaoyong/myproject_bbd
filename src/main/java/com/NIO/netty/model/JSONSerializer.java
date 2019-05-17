package com.NIO.netty.model;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * json序列化器-使用fastjson
 */
public class JSONSerializer implements Serializer
{
    @Override
    public byte getSerializerAlgorithm() {
        return SerializerAlgorithm.JSON;
    }

    @Override
    public byte[] serialize(Object object) {
        //使用fastjson
        return JSON.toJSONBytes(object);
    }

    @Override
    public <T> T deserialize(Class<T> clazz, byte[] bytes) {
        return JSONObject.parseObject(bytes,clazz);
    }
}
