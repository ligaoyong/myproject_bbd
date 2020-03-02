package com.session.redis.sso;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 * 测试sso单点登录
 *  基于cookie根域、路径根的机制
 *  测试通过
 *
 * @author ligaoyong@gogpay.cn
 * @date 2020/1/19 17:21
 */
@RestController
@RequestMapping("sso")
@SuppressWarnings("all")
public class SSOController {

    private static final String CURRENT_LOGIN_USER = "current_login_user:";

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @GetMapping("/login")
    public String login(HttpServletRequest request, HttpServletResponse response){
        Cookie cookie = new Cookie("seesionId", UUID.randomUUID().toString());
        cookie.setHttpOnly(true);
        //设置路径 只有在访问 /sso/app开头的路径才会带上这个这个cookie
        // 那么在A系统登录后设置好cookie 只要B系统等访问路径以sso/app开头 就会携带这个Cookie
        // 这样我们就能拿到用户的信息 避免多次登录
        cookie.setPath("/sso/app");
        response.addCookie(cookie);
        return "ok";
    }

    @GetMapping("/app/1")
    public String app1Info(HttpServletRequest request, HttpServletResponse response){
        String res = "";
        //能得到cookie
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies){
            res += cookie.getName() + " : " + cookie.getValue();
            res += "\r\n";
        }
        return res;
    }

    @GetMapping("/app/2")
    public String app2Info(HttpServletRequest request, HttpServletResponse response){
        String res = "";
        //能得到cookie
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies){
            res += cookie.getName() + " : " + cookie.getValue();
            res += "\r\n";
        }
        return res;
    }

    @GetMapping("info")
    public String info(HttpServletRequest request, HttpServletResponse response){
        String res = "";
        //不能得到cookie
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies){
            res += cookie.getName() + " : " + cookie.getValue();
            res += "\r\n";
        }
        return res;
    }
}
