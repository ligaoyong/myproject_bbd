package com.bbd.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by wuyongchong on 2018/9/25.
 */
public class TestRuntime3 {
    private static Logger logger = LoggerFactory.getLogger("TestRuntime3");
    public static void main(String[] args) {
        try {
            throw new Exception("heheheh");
        }catch (Exception e){
            logger.error("error={},{}",e.getMessage(),"aaaaa",e);
        }
    }
}
