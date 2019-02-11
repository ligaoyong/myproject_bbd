package com.project;

import com.google.common.base.Charsets;
import org.apache.coyote.Response;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * http服务器
 */
public class HttpServer {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket();
        serverSocket.bind(new InetSocketAddress("localhost",8888));
        while (true) {
            try (Socket socket = serverSocket.accept();
                    InputStream inputStream = socket.getInputStream();
                    OutputStream outputStream = socket.getOutputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream))) {

                Stream<String> lines = reader.lines();
                List<String> strings = lines.collect(Collectors.toList());
                System.out.println("接受到http请求信息-------");

                //构建response 返回给浏览器路径信息
                /*String response = "HTTP/1.1 200 OK\n" +
                        "Server: Apache-Coyote/1.1\n"+
                        "Content-Length: "+strings.get(0).length()+"\n"+
                        "content-type: text/html;charset=utf-8\n"+
                        "Date: "+ new Date().toString()+"\n\n"+
                        strings.get(0);*/
                String response1 = "HTTP/1.1 200 OK\r\n" +
                        "Cache-Control: private\r\n" +
                        "Connection: Keep-Alive\r\n" +
                        "Content-Type: text/html;charset=utf-8\r\n" +
                        "Date: "+new Date().toString()+"\r\n" +
                        "Server: BWS/1.1\r\n";

                System.out.println("response：\n"+response1);
                writer.write(response1);
                writer.write("111111111");
                writer.flush();
                /*outputStream.write(response1.getBytes(Charsets.UTF_8));
                outputStream.flush();*/

            }
        }

    }
}
