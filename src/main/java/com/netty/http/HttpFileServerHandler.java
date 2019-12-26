package com.netty.http;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.DelegatingChannelPromiseNotifier;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.handler.stream.ChunkedFile;
import org.apache.http.client.methods.HttpHead;

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
        final String path = sanitizeUri(uri);
        if (path == null){
            sendError(ctx, "路径错误3");
            return;
        }
        File file = new File(path);
        if (file.isHidden() || !file.exists()){
            sendError(ctx,"文件不存在4");
            return;
        }
        if (!file.isFile()){
            sendError(ctx,"不是一个文件5");
            return;
        }

        try(RandomAccessFile randomAccessFile = new RandomAccessFile(file,"r")){
            long fileLength = randomAccessFile.length();
            DefaultFullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK);
            setContentLenth(response, fileLength);
            setContentTypeHeader(response, file);
//            response.content().writeBytes()
            if (isKeepAlive(request)){
                response.headers().set("connection", HttpHeaderValues.KEEP_ALIVE);
            }
            ChannelFuture future = ctx.write(new ChunkedFile(randomAccessFile, 0, fileLength, 8192), ctx.newProgressivePromise());
            //future.addListener(new DelegatingChannelPromiseNotifier())
        }
    }

    private void setContentTypeHeader(DefaultHttpResponse response, File file) {
        response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/html;charset=UTF_8");
    }

    private void setContentLenth(DefaultHttpResponse response, long fileLength) {
        response.headers().set(HttpHeaderNames.CONTENT_LENGTH, fileLength);
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
        uri = uri.replace("/", File.separator);
        if (uri.contains(File.separator+".") || uri.contains("."+File.separator) || uri.startsWith(".") || uri.endsWith(".")){
            return null;
        }
        return System.getProperty("user.dir")+File.separator+uri;
    }

    private void sendError(ChannelHandlerContext ctx, String s) {
        DefaultFullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.BAD_REQUEST);
        response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/html;charset=UTF_8");
//        response.headers().set(HttpHeaderNames.CONTENT_LENGTH, s.length());
        ByteBuf buf = Unpooled.copiedBuffer(s.getBytes(StandardCharsets.UTF_8));
        response.content().writeBytes(buf);
        ctx.writeAndFlush(response);
        ctx.close();
    }
}
