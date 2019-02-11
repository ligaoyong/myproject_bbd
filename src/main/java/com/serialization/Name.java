package com.serialization;

import java.io.Serializable;

/**
 * Created by lgy on 2018/12/4.
 * 实现Serializable 使用默认的序列化方式
 */
public class Name implements Serializable{

    private static final long serialVersionUID = -5809782578272943999L;

    private String lastName;
    private String firstName;
    private String middleName;
    private transient int length;   //序列化排除字段 反序列化时会被初始化为0

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
