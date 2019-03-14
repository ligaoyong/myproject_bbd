package com.mybatis;

import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.CacheNamespaceRef;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

@CacheNamespace //为该接口开启二级缓存
public interface Dao {

    @Select("select * from after_loan_rule;")
    List<Entity> getAll();

    //sql语句在Dao.xml中 不知道为什么 总是加载不了Dao.xml
    /**
     * Dao.xml必须与Dao.java位于同一个包下
     * 当与spring整合以后可以指定mapper的位置
     * @return
     */
    Integer getRows();
}
