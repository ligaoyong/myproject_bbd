package com.blockmvc;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("api")
public class Controller {

    @GetMapping("/mvc")
    public Empolyee rest() {
        String random = "-" + Math.random();
        System.out.println("处理请求的线程：" + Thread.currentThread().getName() + random);
        Empolyee empolyee = new Empolyee();
        empolyee.setId("111");
        empolyee.setName("lgy");

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("继续处理的线程：" + Thread.currentThread().getName() + random);
        return empolyee;
    }
}
