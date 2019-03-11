package com.spring5.web.dispatcherServlet;

import org.springframework.boot.autoconfigure.web.reactive.function.client.WebClientAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

/**
 * Restful客户端
 */
@Controller
public class RestClients {

    @Resource
    private RestTemplate restTemplate;//过世的东西 使用reactive的WebClient



}
