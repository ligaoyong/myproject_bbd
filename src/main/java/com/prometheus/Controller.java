package com.prometheus;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import org.jolokia.util.IoUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import sun.misc.IOUtils;
import sun.nio.ch.IOUtil;

import javax.annotation.Resource;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * TODO
 *
 * @author ligaoyong@gogpay.cn
 * @date 2019/12/11 9:49
 */
@RestController()
@EnableScheduling
public class Controller {

    @Resource
    private RestTemplate restTemplate;

    @GetMapping("/hello")
    public String hello() throws IOException {
        List<String> allPerson = Files.readAllLines(Paths.get("C:\\Users\\dcb\\Desktop\\i茅台\\all成员.txt"));
        String join = String.join("", allPerson);
        JSONObject jsonObject = JSONObject.parseObject(join);
        JSONArray persons = JSONArray.parseArray(jsonObject.getString("userlist"));
        JSONArray allPersonDetail = persons.stream().map(personObject -> JSONObject.parseObject(jsonObject.toString()))
                .map(person -> {
                    HashMap<String, String> map = Maps.newHashMap();
                    map.put("access_token", "uZZdm5hKtdovDiLodyYSuPeISn8_cZiwQEtP_TtYG_000FvjBxl1PlLLNEHHX1miZ-ieslVAgrk2n-Hgl4Fqzw_YlF-Hpdgq1eT84cxpwtOibEoA8Nk3LQ_JtbNx7LQ5GkAV_6k4JJ97FoRUYOFJtBmBAfs437OdqvSqCU_tj-xyAIkEvK1F8lpaGtSbGlvROQU2lkUbUt78oiOk8ShoEg");
                    map.put("userid", person.getString("userid"));
                    map.put("avatar_addr", "1");
                    ResponseEntity<String> forEntity = restTemplate.getForEntity("http://weixin.moutai.com.cn:81/cgi-bin/user/get", String.class, map);
                    return forEntity.getBody();
                })
                .collect(Collectors.toCollection(JSONArray::new));
        String result = allPersonDetail.toJSONString();
        return "hello world";
    }

    @Scheduled(cron = "* * * * * *")
    public void asyn(){
        ResponseEntity<String> forEntity = restTemplate.getForEntity("http://127.0.0.1:8888/hello", String.class);
//        System.out.println(forEntity.getBody());
    }

    @GetMapping("/toDcb")
    public String maotaiGetCode(@RequestParam("code") String code, @RequestParam("state")String state){
        System.out.println("code : "+code);
        System.out.println("state : "+state);
        //获取access_token
        //根据code获取成员信息
        String url = "http://weixin.moutai.com.cn:81/cgi-bin/user/getuserinfo?access_token=ACCESS_TOKEN&code=CODE";
        return "ok";
    }

    public static void main(String[] args) throws SQLException, IOException {
//        Connection connection = DriverManager.getConnection("jdbc:mysql://10.10.9.18:3306/maotai?username=demo&password=demo");
//        Statement statement = connection.createStatement();

        // (userid,name,mobile,department,order,position,positions,gender,email,is_leader_in_dept,avatar,telephone,english_name,status,extattr,qr_code)
        final String sql = "insert into maotai_user_detail values ";
        ArrayList<Integer> arrayList = new ArrayList();
        arrayList.add(36975);
        List<String> allDept = Files.readAllLines(Paths.get("C:\\Users\\dcb\\Desktop\\i茅台\\20200215新增成员详细.txt"));
        String deptStr = String.join("", allDept);
        JSONObject jsonObject = JSONObject.parseObject(deptStr);
        JSONArray jsonArray = JSONArray.parseArray(jsonObject.getString("userlist"));
        String values = jsonArray.stream().map(str -> JSONObject.parseObject(str.toString()))
                .map(user -> {
//                    String aa = dept.getInteger("id") + "," + dept.getInteger("parentid") + "," + "'"+dept.getString("name")+"'" + "," + dept.getInteger("order") + "," + 0;
//                    return "(" + aa + ")";
                    ArrayList<Object> objects = new ArrayList<>();
                    arrayList.set(0, arrayList.get(0) + 1);
                    objects.add(arrayList.get(0));
                    objects.add("'"+user.getString("userid")+"'");
                    objects.add("'" + user.getString("name") + "'");
                    objects.add("'" + user.getString("mobile") + "'");
                    objects.add("'" + user.getJSONArray("department").toJSONString() + "'");
                    objects.add("'" + user.getJSONArray("order").toJSONString() + "'");
                    objects.add("'" + user.getString("position") + "'");
                    objects.add("'" + user.getJSONArray("positions").toJSONString() + "'");
                    objects.add("'" + user.getString("gender") + "'");
                    objects.add("'" + user.getString("email") + "'");
                    objects.add("'" + user.getJSONArray("is_leader_in_dept").toJSONString() + "'");
                    objects.add("'"+user.getString("avatar")+"'");
                    objects.add("'"+user.getString("telephone")+"'");
                    objects.add("'"+user.getString("english_name")+"'");
                    objects.add(user.getInteger("status"));
                    objects.add("'"+user.getString("extattr")+"'");
                    objects.add("'"+user.getString("qr_code")+"'");
                    objects.add("'"+LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss"))+"'");
                    objects.add("'"+LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss"))+"'");
                    objects.add(0);
                    objects.add(0);
                    objects.add("'"+LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss"))+"'");
                    objects.add("null");
                    String a = objects.stream().map(Object::toString).collect(Collectors.joining(","));
                    a = "("+a+")";
                    System.out.println(sql + a);
                    return sql + a;
                }).collect(Collectors.joining(";\n"));

        Path path = Paths.get("C:\\Users\\dcb\\Desktop\\i茅台\\20200215新增成员详细.sql");
        Files.createFile(path);
        FileWriter fileWriter = new FileWriter(path.toFile());
        fileWriter.write(values);
        fileWriter.flush();
        fileWriter.close();
        //System.out.println(sql);
        //ResultSet resultSet = statement.executeQuery("select * from character_sets");
    }
}
