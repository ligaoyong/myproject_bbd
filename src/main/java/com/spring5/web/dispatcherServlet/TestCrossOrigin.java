package com.spring5.web.dispatcherServlet;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.CrossOrigin;

/**
 * 跨域问题
 */
@Controller
public class TestCrossOrigin {

    /**
     * 如果不加注解@CrossOrigin的话  则除了本域的Ajax请求响应以外，其他域请求得到的响应都会被浏览器拦截
     * 加这个注解的作用就是告诉浏览器不要拦截(也可以针对特定的域，主要是响应头Access-Control-Allow-Origin在起作用)
     * @param username
     * @return
     * 项目见D:\Program Files\apache-tomcat-7.0.79\webapps\ROOT/ajax.html
     */
    @CrossOrigin(value = {"http://localhost:8080"})
    @GetMapping("AjaxServlet")
    @ResponseBody
    public String testCross(String username){
        return username;
    }

}
