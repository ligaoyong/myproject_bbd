package com.oauth2;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

/**
 * github 授权流程 : 如同企业微信 i茅台
 *
 * @author ligaoyong@gogpay.cn
 * @date 2020/3/5 14:54
 */
@Controller
public class LoginController {

    @Resource
    private RestTemplate restTemplate;

    @Value("${client_id:12cd0a26f4c838dc3116}")
    private String client_id;

    @Value("${client_secret:285a39753745d0f4bb2e22abbcaba181314e6777}")
    private String client_secret;

    @GetMapping("/login")
    public String login(){

        /** 0、参考文档：http://www.ruanyifeng.com/blog/2019/04/github-oauth.html */

        /** 1、先去github上应用登记 获取Client ID、Client Secret    **/
        //Client ID
        //12cd0a26f4c838dc3116

        //Client Secret
        //285a39753745d0f4bb2e22abbcaba181314e6777
        /** 2、调用github的授权地址： */
        String url = "https://github.com/login/oauth/authorize?client_id={CLIENT_ID}&redirect_uri={REDIRECT_URL}";
        url = url.replace("{CLIENT_ID}", "12cd0a26f4c838dc3116")
                .replace("{REDIRECT_URL}","http://localhost:8080/oauth/redirect");
        /** 3、重定向到github的登录授权页面，进行登录授权后，github会帮我们重定向到回调地址：/oauth/redirect */
        return "redirect:"+url;
    }

    /**
     * 接收github的回调(回调到本地环境，生产环境要提供站点的外网回调接口)
     * @return
     */
    @GetMapping("/oauth/redirect")
    @ResponseBody
    public JSONObject receiveCode(@RequestParam("code") String code){
        /** 4、github回调当前地址,接收github传来的的code */
        System.out.println("code : "+code);

        /** 5、使用code请求令牌 */
        String url = "https://github.com/login/oauth/access_token?client_id={client_id}&client_secret={client_secret}&code={code}";
        url = url.replace("{client_id}", client_id).replace("{client_secret}", client_secret)
                .replace("{code}", code);

        JSONObject jsonObject = restTemplate.postForObject(url, null, JSONObject.class);
        //得到token
        String accessToken = jsonObject.getString("access_token");
        System.out.println("令牌信息："+jsonObject.toJSONString());

        /** 6、使用token去请求用户数据 */
        String requestDataUrl = "https://api.github.com/user";
        //将token放到http请求头里面
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization","token "+accessToken);
        HttpEntity httpEntity = new HttpEntity(httpHeaders);

        ResponseEntity<JSONObject> responseEntity = restTemplate.exchange(requestDataUrl, HttpMethod.GET, httpEntity, JSONObject.class);
        JSONObject user = responseEntity.getBody();
        System.out.println("user : "+user.toJSONString());
        return user;
    }

}
