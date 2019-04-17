package com.serialization;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import org.junit.Test;

import java.io.*;

/**
 * java内置的序列化方式
 */
public class JdkSerial {

    @Data
    @ToString
    @AllArgsConstructor
    public static class Persion implements Serializable{
        String name;
        Integer age;
    }

    private byte[] global_bytes;

    /**
     * 序列化
     * @throws IOException
     */
    @Test
    public void serial() throws IOException, ClassNotFoundException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(new Persion("张三",20));
        byte[] bytes = byteArrayOutputStream.toByteArray();
        global_bytes = bytes;
        System.out.println(bytes);
        reSerial();
    }

    /**
     * 反序列化
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public void reSerial() throws IOException, ClassNotFoundException {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(global_bytes);
        ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
        Object o = objectInputStream.readObject();
        System.out.println(o);
    }

}
