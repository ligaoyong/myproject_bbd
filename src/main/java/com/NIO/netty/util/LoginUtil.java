package com.NIO.netty.util;

import com.NIO.netty.model.Attributes;
import io.netty.channel.Channel;
import io.netty.util.Attribute;

/**
 * 登陆
 */
public class LoginUtil {
    public static void markAsLogin(Channel channel) {
        channel.attr(Attributes.LOGIN).set(true);
    }

    public static boolean hasLogin(Channel channel) {
        Attribute<Boolean> loginAttr = channel.attr(Attributes.LOGIN);

        return loginAttr.get() != null;
    }
}
