package com.mybatis.springMybatis;

import com.mybatis.Entity;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 测试mybatis整合spring
 */
@RestController
public class Controller {

    @Resource
    private Dao dao; //本质上也还是MapperProxy

    @GetMapping("/all")
    public List<Entity> getAll(){
        return dao.getAll();
    }

    @GetMapping("/count")
    public Integer getCount(){
        return dao.getRows();
    }


}
