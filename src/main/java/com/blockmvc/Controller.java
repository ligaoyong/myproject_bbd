package com.blockmvc;

import io.netty.handler.codec.http.HttpHeaderValues;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
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

    /**
     * 测试分块、动态写回数据的技术
     *  测试成功：transfer-encoding、Content-Type必不可少
     */
    @GetMapping("chunked")
    public void testChunked(HttpServletResponse response) throws IOException, InterruptedException {
        //设置响应头transfer-encoding: chunked
        response.addHeader(HttpHeaders.TRANSFER_ENCODING, HttpHeaderValues.CHUNKED.toString());
        //Content-Type: text/html;charset=UTF-8
        response.addHeader(HttpHeaders.CONTENT_TYPE,"text/html;charset=UTF-8");
        PrintWriter writer = response.getWriter();
        writer.write("hahahaha");
        writer.flush();

        //动态写回数据
        for (int i=0;i<100;i++){
            Thread.sleep(100);
            writer.write("<br></br>"+i);
            writer.flush();
        }
        writer.close();
    }
}
