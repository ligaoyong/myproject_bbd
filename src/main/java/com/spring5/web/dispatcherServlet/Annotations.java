package com.spring5.web.dispatcherServlet;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpSession;
import java.io.Reader;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@SessionAttributes("sessionAttributes")
public class Annotations {

    @GetMapping("/owners/{ownerId}/pets/{petId}")
    //@ResponseStatus(value = HttpStatus.OK,reason = "成功！！")
    @ResponseBody
    public Integer findPet(@PathVariable Long ownerId, @PathVariable Long petId) {
        // ...
        System.out.println(ownerId);
        System.out.println(petId);
        return 1;
    }

    @GetMapping(value = "testParamAndHeaders",headers = {"header1=header1"},params = {"param1=param1"})
    @ResponseBody
    //@RequestHeader映射请求头 @RequestParam映射请求参数
    public Integer testParamAndHeaders(@RequestHeader Optional<String> header1, @RequestParam Optional<String> param1){
        System.out.println(header1.orElse("no"));
        //System.out.println(header1.orElseGet(()->"no"));
        System.out.println(param1.orElse("no"));
        return 1;
    }

    @GetMapping(value = "testConsumerAndProduct",
            //consumes与请求头Content-Type对应，products与请求头Accept对应
            consumes = MediaType.TEXT_PLAIN_VALUE,produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    @ResponseBody
    public Integer testConsumerAndProduct(){
        return 1;
    }

    //不通
    /*@GetMapping("testMethodParam")
    @ResponseBody
    public Integer testMethodParam(WebRequest webRequest, NativeWebRequest nativeWebRequest,
                                   HttpSession httpSession, HttpMethod httpMethod, Locale locale,
                                   TimeZone timeZone,*//* Reader reader, Writer writer, *//*@MatrixVariable HashMap map,
                                   *//*@CookieValue Cookie cookie,*//* Model model, ModelMap modelMap,
                                   @ModelAttribute HashMap mapAttribute){
        return 1;
    }
*/
    @GetMapping("testReturn1")
    public ResponseEntity<String> testReturn1(){
        return ResponseEntity.ok("succeed!!");
    }

    @GetMapping("testRequestParam")
    @ResponseBody
    public Integer testRequestParam(@RequestParam Map<String,String> params,
                                    @RequestHeader Map<String,String> headers,
                                    @CookieValue("JSESSIONID") String cookie){
        params.forEach((k,v)-> System.out.println(k+" : "+v));
        headers.forEach((k,v)-> System.out.println(k+" : "+v));
        System.out.println(cookie);
        return 1;
    }
    static class Account{
        String name;
        String password;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }
    static class MyParam{
        String param1;
        String param2;

        public String getParam1() {
            return param1;
        }

        public void setParam1(String param1) {
            this.param1 = param1;
        }

        public String getParam2() {
            return param2;
        }

        public void setParam2(String param2) {
            this.param2 = param2;
        }
    }
    /**
     * 下面实例说明@ModelAttribute的作用：
     *  1：被@ModelAttribute注释的方法会在此controller每个方法执行前被执行
     *  2：您可以在方法参数上使用@ModelAttribute注释从模型访问属性，或者在不存在的情况下实例化属性
     *  3：模型属性还与名称与字段名称匹配的HTTP Servlet请求参数的值重叠。这称为数据绑定
     */
    @ModelAttribute //将返回值Account作为模型属性
    public Account getAccount(){
        Account account = new Account();
        account.setName("account_name");
        account.setPassword("account_password");
        return account;
    }

    @GetMapping("testModelAttribute")
    @ResponseBody
    public Integer testModelAttribute(@ModelAttribute Account account,//注入模型属性 没有模型属性则实例化
                                      @ModelAttribute MyParam myParam, BindingResult result){//数据绑定、绑定结果
        if(result.hasErrors()) {//检查数据绑定时出现的错误，如果方法参数声明有BindingResult 则传递，否则抛出异常
            result.getAllErrors().forEach(System.out::println);
        }
        System.out.println(account.toString());
        return 1;
    }

    /**
     * 以下实例说明@SessionAttributes的作用
     * ：@SessionAttributes用于在请求之间的HTTP Servlet会话中存储模型属性。它是一个类型级别的注释
     */
    @GetMapping("testSessionAttributes")
    @ResponseBody
    public Integer testSessionAttributes(String sessionAttributes){//在类上@SessionAttributes("sessionAttributes")
                                                                    // 标注为会话属性
        return 1;
    }

    @GetMapping("getSessionAttributes") //前提是两次请求必须在同一会话中
    @ResponseBody
    public Integer getSessionAttributes(@SessionAttribute String sessionAttributes){//获取会话属性
        return 1;
    }

    /**
     * 以下实例说明重定向数据传递的实现:RedirectAttributes
     */
    @GetMapping("testRedirectAttributes")
    public String testRedirectAttributes(RedirectAttributes redirectAttributes){
        //方法参数RedirectAttributes会被重定向 如果没有，则重定向模型的所有属性
        redirectAttributes.addAttribute("attribute1", "attribute1");
        redirectAttributes.addAttribute("attribute2", "attribute2");
        return "redirect:/getRedirectAttributes";
        //重定向http://localhost:8080/getRedirectAttributes?attribute1=attribute1&attribute2=attribute2
    }
    @GetMapping("getRedirectAttributes")
    @ResponseBody
    public Integer getRedirectAttributes(String attribute1,String attribute2){
        //获取重定向过来的属性
        System.out.println(attribute1+"---"+attribute2);
        return 1;
    }

    /**
     * 一下实例说明jackson
     * 该视图只允许呈现对象中所有字段的子集。
     * 要将其与@ResponseBody或ResponseEntity控制器方法一起使用，
     * 您可以使用Jackson的@JsonView注释来激活序列化视图类
     */
    static class User{

        interface ViewUsername{}    //只序列化username的标记
        interface Viewpassword{}    //只序列化password的标记
        interface ViewAll{}         //序列化所有的标记

        @JsonView({ViewUsername.class,ViewAll.class})
        String username;
        @JsonView({Viewpassword.class,ViewAll.class})
        String password;
        public String getUsername() {
            return username;
        }
        public void setUsername(String username) {
            this.username = username;
        }
        public String getPassword() {
            return password;
        }
        public void setPassword(String password) {
            this.password = password;
        }
    }

    @GetMapping("testJackson")
    @ResponseBody
    // @JsonView(User.ViewAll.class) {"username":"username","password":"password"} //指定序列化的标记
    @JsonView(User.ViewUsername.class) //{"username":"username"}
    public User testJackson(){
        User user = new User();
        user.setUsername("username");
        user.setPassword("password");
        return user;
    }

    /**
     * 以下实例说明数据绑定DataBinder
     */
    @InitBinder
    public void initDataBinder(WebDataBinder binder){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        //向数据绑定器中注册自定义的类型转化器
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
        //也可以添加本地验证器
        binder.addValidators(null);
    }

}
