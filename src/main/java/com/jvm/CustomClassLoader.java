package com.jvm;
import com.google.common.io.ByteStreams;
import com.google.common.primitives.Bytes;
import org.apache.coyote.http2.ByteUtil;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;

/**
 * 自定义类加载器
 * 必须要实现ClassLoader的findClass方法、findResource方法
 */
public class CustomClassLoader extends ClassLoader{

    /**
     * 去哪个地方找
     * @param name
     * @return
     */
    @Override
    protected URL findResource(String name) {
        String path;
        if (name.contains(".")){
            path = name.replaceAll("\\.", Matcher.quoteReplacement(File.separator)).concat(".class");
        }else {
            path = name +".class";
        }
        try {
            //默认去C:\Users\26403\Documents找
            return new URL("file:\\C:\\Users\\26403\\Documents" + File.separator + path);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        //先获取到类的byte[] 再调用ClassLoader.defineClass获取Class对象
        if (StringUtils.isEmpty(name)){
            throw new ClassNotFoundException();
        }
        URL resource = findResource(name);
        if (resource == null){
            throw new ClassNotFoundException();
        }
        byte[] bytes = new byte[1];
        try(InputStream inputStream = resource.openStream()) {
            bytes = ByteStreams.toByteArray(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return this.defineClass(name,bytes,0,bytes.length);
    }

    public static void main(String[] args) throws ClassNotFoundException {
        CustomClassLoader customClassLoader = new CustomClassLoader();
        Class<?> clazz = customClassLoader.loadClass("com.jvm.TestClassLoader");
        System.out.println("加载成功："+clazz.getClassLoader()); //sun.misc.Launcher$AppClassLoader

        // 这是因为CustomClassLoader的父类是Launcher$AppClassLoader
        // 由Launcher$AppClassLoader去加载 而Launcher$AppClassLoader是能够找到com.jvm.TestClassLoader
        // 但是找不到com.jvm.TestClassLoader1 此时就需要CustomClassLoader去加载了

        Class<?> clazz1 = customClassLoader.loadClass("com.jvm.TestClassLoader1");
        System.out.println("加载成功："+clazz1.getClassLoader()); // com.jvm.CustomClassLoader@28c97a5
    }
}
