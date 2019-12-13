/*
 * Copyright 2013 The Netty Project
 *
 * The Netty Project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package com.netty_http.hello;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.HttpServerExpectContinueHandler;
import io.netty.handler.ssl.SslContext;

public class HttpHelloWorldServerInitializer extends ChannelInitializer<SocketChannel> {

    private final SslContext sslCtx;

    public HttpHelloWorldServerInitializer(SslContext sslCtx) {
        this.sslCtx = sslCtx;
    }

    @Override
    public void initChannel(SocketChannel ch) {
        ChannelPipeline p = ch.pipeline();
        if (sslCtx != null) {
            p.addLast(sslCtx.newHandler(ch.alloc()));
        }
        p.addLast(new HttpServerCodec());
        p.addLast(new HttpServerExpectContinueHandler());
        p.addLast(new HttpHelloWorldServerHandler());

        //处理String消息的Outbound,封装为HttpResponse给HttpServerCodec里的Out处理
        /**
         * 为什么要放在CustomInboundExceptionHandler之前而不是之后？？
         *      因为所有的handler不管是in还是out都按照add的先后顺序被包装成ctx串成链表
         *      再调用fireChannelRead的过程(read),是从链表从前往后（----->）依次查找in类型的handler
         *      再调用ctx的write过程中(write)，则是从当前ctx节点从后往前（<-------）依次查找out类型的handler
         *
         * 所以如果CustomOutHandler在CustomInboundExceptionHandler之后，则在CustomInboundExceptionHandler调用
         * write的时候会往前找，就会找不到CustomOutHandler，所以就会出现问题(本例会出现等待，因为CustomInboundExceptionHandler
         * 返回String，往前找只能找到HttpServerCodec，而HttpServerCodec不会处理String类型的消息)
         */
        p.addLast(new CustomOutHandler());
        //添加全局异常处理器
        p.addLast(new CustomInboundExceptionHandler());

    }
}
