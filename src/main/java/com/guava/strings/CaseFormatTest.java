package com.guava.strings;

import com.google.common.base.CaseFormat;
import com.google.common.base.Strings;
import org.junit.Test;

/**
 * Created by wuyongchong on 2018/11/5.
 * 格式化器
 */
public class CaseFormatTest {

    /**
     * CaseFormat是一个方便的小类，用于转换ASCII大小写约定——例如，编程语言的命名约定。支持的格式包括:
     *  Format	            Example
     *  LOWER_CAMEL	        lowerCamel
     *  LOWER_HYPHEN	    lower-hyphen
     *  LOWER_UNDERSCORE	lower_underscore
     *  UPPER_CAMEL	        UpperCamel
     *  UPPER_UNDERSCORE	UPPER_UNDERSCORE
     */
    @Test public void test1(){
        //使用简单 例子
        String constantName = CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, "CONSTANT_NAME");
        System.out.println(constantName);   //constantName

        //Strings工具类
        System.out.println(Strings.isNullOrEmpty(""));
        System.out.println(Strings.isNullOrEmpty(null));
    }
}
