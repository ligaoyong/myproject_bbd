package com.guava.Basic_Utilities;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import org.junit.Test;

/**
 * Created by wuyongchong on 2018/11/2.
 * 对象的公共方法
 */
public class Objects_Common {

    @Test
    public void test1(){
        Objects.equal(null, "");
        Objects.hashCode("hahahah");
        //toString
        MoreObjects.toStringHelper("").add("x", 1);
    }

}
