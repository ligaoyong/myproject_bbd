package com.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 登录、访问
 *
 * @author ligaoyong@gogpay.cn
 * @date 2019/11/18 11:06
 */
@RestController
@RequestMapping("api")
public class JwtController {

    @RequestMapping("login")
    public String login(String userName, HttpServletRequest request, HttpServletResponse response) {
        System.out.println("login...");
        /************生成token**************/

        //1、定义算法 使用一个密钥
        Algorithm algorithm = Algorithm.HMAC256("secret_1111");

        //2、生成token
        String token = JWT.create()
                //声明自定义的数据
                .withClaim("userName", userName)
                //使用指定的算法生成token
                .sign(algorithm);
        //3、使用redis来保存token的过期时间 略

        //4、返回token给客户端 这里是放在cookie里面
        Cookie cookie = new Cookie("token",token);
        cookie.setPath("/");
        response.addCookie(cookie);

        System.out.println("login success");
        return "login success,token:"+token;
    }

    @GetMapping("user/data")
    public String data(HttpServletRequest request){
        //1、获取token
        String token = "";
        Cookie[] cookies = request.getCookies();
        if (cookies != null){
            for (Cookie cookie : cookies){
                if ("token".equals(cookie.getName())){
                    token = cookie.getValue();
                }
            }
        }
        if (StringUtils.isEmpty(token)){
            return "no login";
        }
        System.out.println("token:"+token);
        //2、验证token
        DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC256("secret_1111"))
                .build()
                .verify(token);
        //3、从token中获取用户信息
        Map<String, Claim> claims = decodedJWT.getClaims();
        Claim userName = claims.get("userName");
        System.out.println("userName:"+userName.asString());

        return "data :"+claims.toString();
    }

}
