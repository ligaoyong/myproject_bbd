package com.spring5.web.dispatcherServlet;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;

import java.io.IOException;

/**
 * 异步请求
 */
@Controller
public class AsyncRequest {
    DeferredResult<String> deferredResult;
    /**
     * DeferredResult
     */
    @GetMapping("/quotes")
    @ResponseBody
    public DeferredResult<String> quotes() {
        deferredResult = new DeferredResult<String>();
        return deferredResult;
    }
    @GetMapping("/active")
    @ResponseBody
    public Integer quotes1() {
        deferredResult.setResult("Ok!!!!");
        return 1;
    }

    /**
     * Callable同DeferredResult
     */


    /**
     * Http流：生成多个异步返回值
     * 1:ResponseBodyEmitter实现
     */
    ResponseBodyEmitter emitter;
    @GetMapping("/events")
    public ResponseBodyEmitter handle() {
        emitter = new ResponseBodyEmitter();
        // Save the emitter somewhere..
        return emitter;
    }
    @GetMapping("writeToEmitter")
    @ResponseBody
    public Integer writeToEmitter() throws IOException, InterruptedException {
        emitter.send("第一步。。。。。");
        Thread.sleep(10*1000);
        emitter.send("第二步。。。。。");
        Thread.sleep(10*1000);
        emitter.complete();
        return 1;
    }
}
