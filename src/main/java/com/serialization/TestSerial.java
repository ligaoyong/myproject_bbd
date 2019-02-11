package com.serialization;

import org.junit.Test;
import sun.misc.ObjectInputFilter;

import java.io.*;

/**
 * Created by lgy on 2018/12/4.
 * 测试序列化
 */
public class TestSerial {

    @Test
    public void serializable() throws Exception {
        Name my = new Name();
        my.setFirstName("li");
        my.setMiddleName("gao");
        my.setLastName("yong");
        my.setLength(9);
        //序列化到磁盘(也可序列化到网络)
        serialize(my);
    }

    @Test
    public void reserializable() throws Exception {
        //反序列化
        Name my = reserialize();
        System.out.println(my);
    }

    /**
     * 序列化到磁盘
     * @param name
     * @throws IOException
     */
    private void serialize(Name name) throws IOException {
        //FileOutputStream也可以换成网络流
        ObjectOutputStream outputStream =
                new ObjectOutputStream(new FileOutputStream(new File("D:\\nameObject")));
        //默认序列化方法
        outputStream.writeObject(name);
        outputStream.close();
    }

    /**
     * 反序列化方法
     * @return
     */
    private Name reserialize() throws IOException, ClassNotFoundException {
        try(ObjectInputStream inputStream =
                new ObjectInputStream(new FileInputStream(new File("D:\\nameObject")))){
            //反序列化
            return (Name) inputStream.readObject();
        }

    }
}
