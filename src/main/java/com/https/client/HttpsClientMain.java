package com.https.client;

import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.ssl.TrustStrategy;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.jdbc.DataSourceHealthIndicatorAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.XADataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.SSLContext;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;

/**
 * https client
 * 需要添加httpclient依赖
 */
public class HttpsClientMain {

    public static void main(String[] args) throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException {
        //证书的信任策略 这里返回true 表示信任所有的证书
        // chain表示服务器返回的证书 authType表示认证类型
        TrustStrategy trustStrategy = (X509Certificate[] chain, String authType) -> true;
        //创建SSL上下文
        SSLContext sslContext = SSLContexts.custom().loadTrustMaterial(trustStrategy).build();
        //创建SSL连接工厂
        // 若出现Certificate for <localhost> doesn't match any of the subject alternative names: []
        // 则加上NoopHostnameVerifier.INSTANCE，标识不验证域名
        SSLConnectionSocketFactory sslConnectionSocketFactory = new SSLConnectionSocketFactory(sslContext, NoopHostnameVerifier.INSTANCE);
        //创建http客户端
        CloseableHttpClient httpClient = HttpClients.custom()
                .setSSLSocketFactory(sslConnectionSocketFactory).build();

        RestTemplate restTemplate = new RestTemplate(new HttpComponentsClientHttpRequestFactory(httpClient));

        ResponseEntity<String> forEntity = restTemplate.getForEntity("https://127.0.0.1:443/hello", String.class);
        System.out.println(forEntity.getBody());
    }

}
