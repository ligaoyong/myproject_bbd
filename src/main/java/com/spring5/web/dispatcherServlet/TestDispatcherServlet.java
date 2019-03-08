package com.spring5.web.dispatcherServlet;

import com.google.common.collect.Maps;
import com.google.common.io.Files;
import org.aspectj.util.FileUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.support.RequestContextUtils;
import sun.misc.ClassLoaderUtil;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@Controller
public class TestDispatcherServlet {

    //第一次请求的时候才会初始化
    @Resource
    private DispatcherServlet dispatcherServlet;
    @Resource
    private WebApplicationContext webApplicationContext;

    @GetMapping("test1")
    @ResponseBody
    public Map<String,Object> test1(Locale locale){
        System.out.println(locale.toString());
        HashMap<String, Object> map = Maps.newHashMap();
        map.put("a", 1);
        map.put("b", 2);
        return map;
    }

    @GetMapping("index")
    public String index(){
        return "index";
    }

    @PostMapping(value = "upload")
    @ResponseBody
    public String upload(MultipartFile file, HttpServletRequest httpServletRequest) throws IOException {
        String originalFilename = file.getOriginalFilename();
        String name = file.getName();
        long size = file.getSize();
        byte[] bytes = file.getBytes();
        //String realPath = webApplicationContext.getServletContext().getRealPath("/static");
        file.transferTo(new File("E:\\workspace\\myproject\\src\\main\\resources\\static/test.txt"));
        return "OK";
    }
}
