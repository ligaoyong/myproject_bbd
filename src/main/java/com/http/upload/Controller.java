package com.http.upload;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

/**
 * TODO
 *
 * @author ligaoyong@gogpay.cn
 * @date 2020/1/20 14:24
 */
@RestController
public class Controller {

    @PostMapping("upload")
    public String upload(@RequestParam("uploadFile") MultipartFile uploadFile, /*@RequestParam("userName")*/String userName, HttpServletRequest request){
        String originalFilename = uploadFile.getOriginalFilename();
        System.out.println("originalFilename ： "+originalFilename);
        System.out.println("name : "+userName);
        return "ok";
    }
}
