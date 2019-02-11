package com.serialization;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * Created by lgy on 2018/12/4.
 * 实现Serializable 且使用自定义序列化方式
 * 关键在于手写：writeObject、readObject
 */
public class Name2 implements Serializable{

    private static final long serialVersionUID = -580978257827294399L;

    private String lastName;
    private String firstName;
    private String middleName;
    private transient int length;   //序列化排除字段 反序列化时会被初始化为0

    //序列化方法 需要手写
    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();   //默认的序列化方式：序列化所有非transient字段
        out.writeUTF(lastName+"--");
        out.writeUTF(firstName+"--");
        out.writeUTF(middleName+"--");
        out.writeInt(length+8);
        System.out.println("序列化内容完成。。。");
    }

    //反序列方法 需要手写
    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject(); //默认的反序列化方式：反序列化所有非transient字段
        lastName = in.readUTF();
        firstName = in.readUTF();
        middleName = in.readUTF();
        length = in.readInt();
        System.out.println("反序列化内容完成。。。");
    }

    @Override
    public String toString() {
        return "Name{" +
                "lastName='" + lastName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", middleName='" + middleName + '\'' +
                ", length=" + length +
                '}';
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }
}
