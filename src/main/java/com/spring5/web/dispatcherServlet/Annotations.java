package com.spring5.web.dispatcherServlet;

import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.context.request.async.DeferredResult;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpSession;
import java.io.Reader;
import java.io.Writer;
import java.util.*;

@Controller
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



}
