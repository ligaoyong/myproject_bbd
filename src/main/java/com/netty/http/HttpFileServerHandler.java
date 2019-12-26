package com.netty.http;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.handler.codec.http.*;
import io.netty.handler.stream.ChunkedFile;
import org.aspectj.util.FileUtil;

import java.io.File;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

import static io.netty.handler.codec.http.HttpUtil.isKeepAlive;

/**
 * TODO
 *
 * @author ligaoyong@gogpay.cn
 * @date 2019/12/26 11:00
 */
public class HttpFileServerHandler extends SimpleChannelInboundHandler<FullHttpRequest> {

    public HttpFileServerHandler(String url) {
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest request) throws Exception {
        if (!request.decoderResult().isSuccess()){
            sendError(ctx, "错误1");
            return;
        }
        if (request.method() != HttpMethod.GET){
            sendError(ctx,"错误2");
            return;
        }
        final String uri = request.uri();
        if (uri.contains("favicon.ico")){
            ctx.close();
            return;
        }
        final String path = sanitizeUri(uri);
        if (path == null){
            sendError(ctx, "路径不存在");
            return;
        }
        File file = new File(path);
        if (!file.exists()){
            sendError(ctx, "文件不存在");
            return;
        }
        if (file.isDirectory()){
            //目录
            FullHttpResponse response = buildResponse(file);
            ctx.writeAndFlush(response);
            ctx.close();
        }else {
            //文件
            try(RandomAccessFile randomAccessFile = new RandomAccessFile(file,"r")){
                long fileLength = randomAccessFile.length();
                DefaultFullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK);
                response.headers().set("Content-Type", "text/html;charset=utf-8");
                //response.headers().set(HttpHeaderNames.TRANSFER_ENCODING, HttpHeaderValues.CHUNKED);
                if (isKeepAlive(request)){
                    response.headers().set("connection", HttpHeaderValues.KEEP_ALIVE);
                }
                //直接写回文件可以
                response.content().writeBytes(FileUtil.readAsByteArray(file));
                ctx.writeAndFlush(response);
                //文件分块写回 没有测试通过
                /*ChannelFuture future = ctx.write(new ChunkedFile(randomAccessFile, 0, fileLength, 81), ctx.newProgressivePromise());
                future.addListener(new ChannelProgressiveFutureListener() {
                    @Override
                    public void operationProgressed(ChannelProgressiveFuture future, long progress, long total) throws Exception {
                        System.out.println("操作中");
                    }

                    @Override
                    public void operationComplete(ChannelProgressiveFuture future) throws Exception {
                        System.out.println("操作完毕");
                        future.channel().flush();
                        //future.channel().close();
                    }
                });*/
            }
        }


    }

    /**
     * 构建目录返回信息
     * @param file
     * @return
     */
    private FullHttpResponse buildResponse(File file){
        String dir = System.getProperty("user.dir");
        FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,HttpResponseStatus.OK);
        response.headers().set("Content-Type", "text/html;charset=utf-8");
        StringBuilder buf = new StringBuilder();
        buf.append("<html><body><ul>\r\n");
        buf.append("<li>目录：<a href=\"../\">..</a></li>\r\n");
        for (File f: file.listFiles()){
            String f_path = file.getPath() + File.separator + f.getName();
            f_path = f_path.replace(dir, "");
            buf.append("<li>"+"<a href="+"http://localhost:8080/"+f_path+">"+f.getName()+"</a></li>\r\n");
        }
        buf.append("</ul></body></html>\r\n");
        response.content().writeBytes(buf.toString().getBytes(StandardCharsets.UTF_8));
        return response;
    }

    /**
     * 对uri进行消毒
     * @param uri
     * @return
     */
    private String sanitizeUri(String uri) {
        try {
            URLDecoder.decode(uri, StandardCharsets.UTF_8.name());
        } catch (UnsupportedEncodingException e) {
            try {
                URLDecoder.decode(uri, StandardCharsets.ISO_8859_1.name());
            } catch (UnsupportedEncodingException e1) {
                e1.printStackTrace();
            }
        }
        if (uri.startsWith("/")){
            uri = uri.replaceFirst("/", "");
        }
        uri = uri.replace("/", File.separator);
        return System.getProperty("user.dir")+File.separator+uri;
    }

    /**
     * 发送错误消息
     * @param ctx
     * @param s
     */
    private void sendError(ChannelHandlerContext ctx, String s) {
        ByteBuf buf = Unpooled.copiedBuffer(s.getBytes(StandardCharsets.UTF_8));
        DefaultFullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK);
        response.headers().set("Content-Type", "text/html;charset=utf-8");
        response.content().writeBytes(buf);
        ctx.writeAndFlush(response);
        ctx.close();
    }
}
