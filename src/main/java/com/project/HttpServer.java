package com.project;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

/**
 * http服务器
 */
public class HttpServer {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket();
        serverSocket.bind(new InetSocketAddress("localhost",9999));
        //serverSocket.setSoTimeout(2000);
        while (true) {
            try (Socket socket = serverSocket.accept();
                 InputStream inputStream = socket.getInputStream();
                 OutputStream outputStream = socket.getOutputStream();
                 BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                 BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream));
            ) {
                System.out.println("接受到http请求信息-------");
                //构建response 返回给浏览器路径信息
                String response1 = "HTTP/1.1 200 OK\r\n" +
                        "Cache-Control: private\r\n" +
                        "Connection: Keep-Alive\r\n" +
                        "Content-Type: text/html;charset=utf-8\r\n" +
                        "Date: " + new Date().toString() + "\r\n" +
                        "Server: BWS/1.1\r\n\r\n"+
                        "hello world！！！";

                System.out.println("响应信息：\n" + response1);
                writer.write(response1);
                writer.flush();
            }
        }

    }
}
