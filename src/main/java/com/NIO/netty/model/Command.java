package com.NIO.netty.model;

/**
 * 指令
 */
public interface Command {
    Byte LOGIN_REQUEST = 1; //登陆请求
    Byte LOGIN_RESPONSE = 2;    //登陆响应
    Byte MESSAGE_REQUEST = 3;   //消息请求
    Byte MESSAGE_RESPONSE = 4;  //消息响应
}
