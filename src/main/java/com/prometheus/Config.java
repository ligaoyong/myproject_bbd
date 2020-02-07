package com.prometheus;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * TODO
 *
 * @author ligaoyong@gogpay.cn
 * @date 2020/2/7 11:08
 */
@Configuration
public class Config {
    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
