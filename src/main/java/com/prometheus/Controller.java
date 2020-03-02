package com.prometheus;

import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

/**
 * TODO
 *
 * @author ligaoyong@gogpay.cn
 * @date 2019/12/11 9:49
 */
@RestController()
@EnableScheduling
public class Controller {

    @Resource
    private RestTemplate restTemplate;

    @GetMapping("/hello")
    public String hello(){
        return "hello world";
    }

    @Scheduled(cron = "* * * * * *")
    public void asyn(){
        ResponseEntity<String> forEntity = restTemplate.getForEntity("http://127.0.0.1:8888/hello", String.class);
//        System.out.println(forEntity.getBody());
    }
}
