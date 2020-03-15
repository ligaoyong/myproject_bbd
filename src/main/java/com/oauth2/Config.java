package com.oauth2;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * TODO
 *
 * @author ligaoyong@gogpay.cn
 * @date 2020/3/5 15:03
 */
@Configuration
public class Config {

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

}
