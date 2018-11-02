package com.bbd.exception;


import org.junit.Test;

/**
 * Created by wuyongchong on 2018/9/19.
 */
public class TestRuntime {

    @Test
    public void consumer(){
        //调用声明检查时异常的方法 需要处理异常
       /* try {
            provider();
        } catch (Exception e) {
            e.printStackTrace();
        }*/

       //调用非检查时异常不用处理 jvm会直接清空线程栈
       provider2();

    }

    public void provider() throws Exception {
        System.out.println("-----");
        throw new Exception("xxxx");
    }

    public void provider2(){
        throw new RuntimeException("xxxxx");
    }


    @Test
    public void test1(){
      try{
          throw new RuntimeException("hahaha");
      }catch (Exception e){
          System.out.println(e);
      }
    }

}
