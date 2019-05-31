package com.redisson;

import org.junit.Before;
import org.junit.Test;
import org.redisson.api.*;
import org.redisson.transaction.TransactionException;

/**
 * Redisson 事务机制
 */
public class Transaction {

    private static RedissonClient redisson = null;

    @Before
    public void before(){
        redisson = RedissonConfig.newSingleInstance();
    }

    /**
     * XA事务：分布式事务
     *      该功能仅适用于Redisson PRO版本
     */
    @Test
    public void test2(){
//        RXAResource xaResource = redisson.getXAResource();
//        globalTransaction.enlistResource(xaResource);
//
//        RTransaction transaction = xaResource.getTransaction();
//        RBucket<String> bucket = transaction.getBucket("myBucket");
//        bucket.set("simple");
//        RMap<String, String> map = transaction.getMap("myMap");
//        map.put("myKey", "myValue");
//
//        transactionManager.commit();
    }

    /**
     * Redisson为RMap、RMapCache、RLocalCachedMap、RSet、RSetCache和RBucket这样的对象提供了具有ACID属性的事务功能。
     * Redisson事务通过分布式锁保证了连续写入的原子性，同时在内部通过操作指令队列实现了Redis原本没有的提交与滚回功能。
     * 当提交与滚回遇到问题的时候，将通过org.redisson.transaction.TransactionException告知用户
     * Redisson事务支持的事务隔离等级为: READ_COMMITTED，即仅读取提交后的结果。
     */
    @Test
    public void test1(){
        RTransaction transaction = redisson.createTransaction(TransactionOptions.defaults());
        RMap<Object, Object> map = transaction.getMap("transactionMap");
        map.put("1", "1");
        RSet<Object> set = transaction.getSet("transactionSet");
        set.add(1);
        //int i = 1 / 0;
        try {
            transaction.commit();
        }catch (TransactionException e){
            transaction.rollback();
        }
    }

}
