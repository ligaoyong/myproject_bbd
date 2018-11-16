package com.enumAndAnnotation;

import org.junit.Test;

/**
 * Created by wuyongchong on 2018/11/16.
 * 枚举
 */
public enum Apple {
    FUJI(1,"a"),    //定义枚举成员时，如果枚举中有普通字段值，则应该传入一个参数
    PIPPIN(2,"b"),
    GRANNY_SMITH(3,"c");

    private Integer size;   //枚举中也可以有普通字段值 还可以为其提供构造器
    private String msg;

    Apple(Integer size, String msg) {
        this.size = size;
        this.msg = msg;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
