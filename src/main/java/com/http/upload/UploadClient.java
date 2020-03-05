package com.http.upload;


import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 模拟上传  测试通过一般  普通参数传不过去
 *
 * @author ligaoyong@gogpay.cn
 * @date 2020/1/20 14:43
 */
public class UploadClient {
    public static void main(String[] args) {
        //注意换行符 一个都不能漏 也一个不能多
        final String newLine = "\r\n";
        final String boundaryPrefix = "--";
        //表单项分割
        String boundary = "-------------6daw7daw82yh24k4235hjk23h32";

        try {
            URL url = new URL("http://localhost:8080/upload");
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

            urlConnection.setRequestMethod("POST");
            urlConnection.setDoOutput(true);
            urlConnection.setDoInput(true);
            urlConnection.setUseCaches(false);
            urlConnection.setConnectTimeout(180000);

            //设置请求头
            urlConnection.setRequestProperty("connection","Keep-Alive");
            urlConnection.setRequestProperty("Charset","UTF-8");
            urlConnection.setRequestProperty("Content-Type","multipart/form-data;boundary="+boundary);

            DataOutputStream outputStream = new DataOutputStream(urlConnection.getOutputStream());

            //1、上传文件
            File file = new File("C:\\Users\\dcb\\Desktop\\测试视频.mp4");
            String str = "";
            str += boundaryPrefix + boundary;
            str += newLine;
            str += "Content-Disposition:form-data;name=\"uploadFile\";filename=\"测试视频.mp4\"";
            str += newLine;
            str += "Content-Type:application/octet-stream";
            str += newLine;
            str += newLine;
            //将参数头写入流中
            outputStream.write(str.getBytes());
            //向流中写入二进制数据
            DataInputStream inputStream = new DataInputStream(new FileInputStream(file));
            byte[] bytes = new byte[1024];
            int len = 0;
            while ((len = inputStream.read(bytes)) != -1){
                outputStream.write(bytes,0,len);
            }
            inputStream.close();
            outputStream.write(newLine.getBytes());
            outputStream.write((boundaryPrefix+boundaryPrefix).getBytes());
            outputStream.write(newLine.getBytes());
            //2、上传普通参数
            String other = "Content-Disposition:form-data;name=\"userName\"";
            other += newLine;
            other += "Content-Type:text/plain;charset=UTF-8";
            other += newLine;
            other += newLine;
            other += "ligaoyong";
            other += newLine;
            //写入流中
            outputStream.write(other.getBytes());
            //3、写入结尾标志
            outputStream.write((boundaryPrefix + boundary + boundaryPrefix).getBytes());
            outputStream.write(newLine.getBytes());
            outputStream.flush();
            outputStream.close();
            System.out.println("发送完成");

            InputStream inputStream1 = urlConnection.getInputStream();
            ObjectInputStream inputStream2 = new ObjectInputStream(inputStream1);
            String response = "";
            while (!(response=inputStream2.readUTF()).equals("")){
                System.out.println(response);
            }
            inputStream1.close();
            inputStream2.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
