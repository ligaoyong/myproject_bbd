package com.session.redis;

import com.alibaba.fastjson.JSONObject;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 单账户登录同一个系统
 *  同一个账户只允许一个客户端在线
 *  登录时检测是否有其他客户端登录 有的话踢掉之前的会话
 * @author ligaoyong@gogpay.cn
 * @date 2020/1/19 15:58
 */
@RestController
@RequestMapping("/single")
@SuppressWarnings("all")
public class SingleController {

    private static final String CURRENT_LOGIN_USER = "current_login_user:";

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @GetMapping("/login/{userName}")
    public String login(HttpServletRequest request, HttpServletResponse response,@PathVariable String userName){
        String res = "";
        HttpSession session = request.getSession();
        String userKey = CURRENT_LOGIN_USER+userName;
        //当前用户已经存在
        if (stringRedisTemplate.hasKey(userKey)){
            res += "踢掉其他的会话信息："+stringRedisTemplate.opsForValue().get(userKey)+"\r\n";
            //设置为无效
            session.invalidate();
            stringRedisTemplate.delete(userKey);
        }
        //创建新的session
        HttpSession newSession = request.getSession(true);
        newSession.setAttribute(userKey,userName);
        //将账号与会话信息关联起来
        stringRedisTemplate.opsForValue().set(userKey,newSession.getId());
        res += "创建新的会话信息：" + newSession.getId();
        return res;
    }

    /**
     * 获取当前最新的会话
     * @param request
     * @return
     */
    @GetMapping("/sessionInfo/{userName}")
    public String sessionInfo(HttpServletRequest request,@PathVariable String userName){
        JSONObject jsonObject = new JSONObject();
        String userKey = CURRENT_LOGIN_USER+userName;
        if (stringRedisTemplate.hasKey(userKey)){
            //最新的登录信息
            String sessionId = stringRedisTemplate.opsForValue().get(userKey);
            HttpSession session = request.getSession();
            //sessionId不一样 代表当前客户端不是最新登录的 已经被踢掉了
            if (!sessionId.equals(session.getId())){
                jsonObject.put("msg", "登录信息已经被踢下线,最新的sessionId："+sessionId);
            }else {
                jsonObject.put("sessionId", sessionId);
            }
        }else {
            jsonObject.put("msg", "没有session信息");
        }
        return jsonObject.toJSONString();
    }
}
