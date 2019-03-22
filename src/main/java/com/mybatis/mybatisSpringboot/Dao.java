package com.mybatis.mybatisSpringboot;

import com.mybatis.Entity;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface Dao {
    @Select("select * from after_loan_rule;")
    List<Entity> getAll();

    Integer getRows();
}
