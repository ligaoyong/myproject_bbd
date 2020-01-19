package com.session.redis;

import com.alibaba.fastjson.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Enumeration;

/**
 * TODO
 *
 * @author ligaoyong@gogpay.cn
 * @date 2020/1/19 14:35
 */
@RestController
public class LoginController {

    /**
     * spring session 也是采用cookie 格式为session=xxxxx
     *
     * 默认情况下，也就是应用程序不介入的情况下：
     * 同一个客户端只会记住上一次登录的用户
     * 问题：
     *      如果同一个用户在不同的客户端登录，会创建不同的session 这样不同的session就会不同步
     * 解决方案：
     *      1、同一个用户允许多个客户端同时在线
     *          1、1 多个会话之间不用共享数据，那么不用管，使用默认即可
     *          1、2 多个会话之间需要通讯(例如A客户端登录的时候告诉B客户端)，那么需要记录账号的多个session
     *      2、只允许一个客户端在线
     *          登录时检查其他客户端会话是否存在，存在则删除其他客户端的会话，创建新的会话
     * @param request
     * @param userName
     * @return
     */

    @GetMapping("/login/{userName}")
    public String login(HttpServletRequest request, @PathVariable String userName){
        HttpSession session = request.getSession();
        session.setAttribute("userName",userName);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("sessionId", session.getId());
        Enumeration<String> attributeNames = session.getAttributeNames();
        while (attributeNames.hasMoreElements()){
            String name = attributeNames.nextElement();
            jsonObject.put(name, session.getAttribute(name));
        }
        return jsonObject.toJSONString();
    }

    @GetMapping("sessionInfo")
    public String sessionInfo(HttpServletRequest request){
        HttpSession session = request.getSession();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("sessionId", session.getId());
        Enumeration<String> attributeNames = session.getAttributeNames();
        while (attributeNames.hasMoreElements()){
            String name = attributeNames.nextElement();
            jsonObject.put(name, session.getAttribute(name));
        }
        return jsonObject.toJSONString();
    }

    @GetMapping("/loginOut/{userName}")
    public String loginOut(HttpServletRequest request, @PathVariable String userName){
        HttpSession session = request.getSession();
        session.invalidate();
        return "退出登录，销毁session";
    }

}
