package com.NIO.netty.util;

import com.NIO.netty.model.Attributes;
import io.netty.channel.Channel;
import io.netty.util.AttributeKey;

import java.util.concurrent.ConcurrentHashMap;

public class SessionUtil {

    private static ConcurrentHashMap<String, Channel> map = new ConcurrentHashMap<>();

    /**
     * 绑定会话：userId--channel
     * @param session
     */
    @SuppressWarnings("all")
    public static void bindSession(Session session,Channel channel){
        map.put(session.getUserId(), channel);
        channel.attr(Attributes.SESSION).set(session);
    }

    /**
     * 解除会话：userId--channel
     * @param channel
     */
    @SuppressWarnings("all")
    public static void unBindSession(Channel channel){
        Session session = (Session)channel.attr(Attributes.SESSION).get();
        map.remove(session.getUserId());
        channel.attr(Attributes.SESSION).set(null);
    }

    public static boolean hasLogin(Channel channel) {

        return channel.hasAttr(Attributes.SESSION);
    }

    public static Session getSession(Channel channel) {

        return (Session) channel.attr(Attributes.SESSION).get();
    }

    public static Channel getChannel(String userId) {

        return map.get(userId);
    }
}
