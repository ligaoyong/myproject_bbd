package com.serialization;

import java.io.*;

/**
 * Created by lgy on 2018/12/4.
 * 实现Externalizable 自定义序列化方式
 */
public class Name1 implements Externalizable{

    private static final long serialVersionUID = -5809782578272943999L;

    private String lastName;
    private String firstName;
    private String middleName;
    private int length;   //序列化排除字段 反序列化时会被初始化为0

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        //自己控制序列化的内容
        out.writeUTF(lastName+"..");
        out.writeUTF(firstName+"..");
        out.writeUTF(middleName+"..");
        out.writeInt(length+10);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        //读取内容 读取顺序必须和写入的不走一致
        lastName = in.readUTF();
        firstName = in.readUTF();
        middleName = in.readUTF();
        length = in.readInt();
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
