package com.mybatis;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 测试
 */
@RestController
public class Controller {

    @Resource
    private SqlSessionFactory sqlSessionFactory;

    @GetMapping("/all")
    public List<Entity> getAll(){
        SqlSession sqlSession = sqlSessionFactory.openSession();
        Dao mapper = sqlSession.getMapper(Dao.class);
        return mapper.getAll();
        /**
         * 由此可知 平时注入的xxxDao本质是sqlSession返回的Mapper
         */
    }

    @GetMapping("/count")
    public Integer getCount(){
        SqlSession sqlSession = sqlSessionFactory.openSession();
        Dao mapper = sqlSession.getMapper(Dao.class);
        return mapper.getRows();
    }
}
