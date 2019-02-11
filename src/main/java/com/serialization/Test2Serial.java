package com.serialization;

import org.junit.Test;

import java.io.*;

/**
 * Created by wuyongchong on 2018/12/4.
 * 测试Serializable 且自定义序列化内容
 */
public class Test2Serial {
    @Test
    public void serializable() throws Exception {
        Name2 my = new Name2();
        my.setFirstName("li");
        my.setMiddleName("gao");
        my.setLastName("yong");
        my.setLength(9);
        //序列化到磁盘(也可序列化到网络)
        //FileOutputStream也可以换成网络流
        ObjectOutputStream outputStream =
                new ObjectOutputStream(new FileOutputStream(new File("D:\\nameObject2")));
        //默认序列化方法
        outputStream.writeObject(my);
        outputStream.close();
    }

    @Test
    public void reserializable() throws Exception {
        try(ObjectInputStream inputStream =
                    new ObjectInputStream(new FileInputStream(new File("D:\\nameObject2")))){
            //反序列化
            Name2 name2 = (Name2) inputStream.readObject();
            System.out.println(name2);
        }
    }
}
