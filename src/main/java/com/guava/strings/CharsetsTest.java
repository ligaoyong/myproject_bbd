package com.guava.strings;

import com.google.common.base.Charsets;
import org.junit.Test;

import java.io.UnsupportedEncodingException;

/**
 * Created by wuyongchong on 2018/11/5.
 * 字符集
 */
public class CharsetsTest {

    @Test public void test1(){
        String s = "aaaaaa";
        try {

            byte[] bytes = s.getBytes("utf-8"); //很挫
            byte[] bytes1 = s.getBytes(Charsets.UTF_8); //高端用法

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

}
