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

}
