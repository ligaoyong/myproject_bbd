package com.prometheus;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * TODO
 *
 * @author ligaoyong@gogpay.cn
 * @date 2019/12/11 9:49
 */
@RestController()
public class Controller {

    @GetMapping("/hello")
    public String hello(){
        return "hello world";
    }
}
