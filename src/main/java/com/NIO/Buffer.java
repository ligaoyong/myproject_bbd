package com.NIO;

import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;

public class Buffer {

    @Test
    public void buffer1() throws IOException {
        RandomAccessFile file =
                new RandomAccessFile("C:\\Users\\john\\Desktop\\二代征信数据.sql", "rw");
        FileChannel channel = file.getChannel();
        ByteBuffer buffer = ByteBuffer.allocate(1024); //1kb
        //读取了多少字节 从通道中读取了 然后写入到缓冲区中 本质上是对缓冲区的写
        int read = channel.read(buffer);
        while (read != -1) {
            buffer.flip(); //切换为读模式 position置为0
            byte[] array = buffer.array();//一次性读完
            System.out.println(new String(array, StandardCharsets.UTF_8));
            buffer.clear(); //清空缓冲区
            read = channel.read(buffer);
        }
        channel.close();//关闭通道
    }


    /**
     * 聚集（gather）写入Channel是指在写操作时将多个buffer的数据写入同一个Channel，
     * 因此，Channel 将多个Buffer中的数据“聚集（gather）”后发送到Channel。
     */
    @Test
    public void gather() throws IOException {
        RandomAccessFile file =
                new RandomAccessFile("C:\\Users\\john\\Desktop\\nio.txt", "w");
        FileChannel channel = file.getChannel();

        ByteBuffer byteBuffer = ByteBuffer.allocate(100);
        ByteBuffer byteBuffer1 = ByteBuffer.allocate(100);

        byteBuffer.put("是撒撒撒撒撒撒撒撒撒撒撒撒撒所".getBytes(StandardCharsets.UTF_8));
        byteBuffer1.put("的凤飞飞身份是付费方式".getBytes(StandardCharsets.UTF_8));

        channel.write(new ByteBuffer[]{byteBuffer, byteBuffer1});
        channel.close();
    }

    /**
     * 分散（scatter）从Channel中读取是指在读操作时将读取的数据写入多个buffer中。
     * 因此，Channel将从Channel中读取的数据“分散（scatter）”到多个Buffer中。
     */
    @Test
    public void scatter() throws IOException {
        RandomAccessFile file =
                new RandomAccessFile("C:\\Users\\john\\Desktop\\nio.txt", "r");
        FileChannel channel = file.getChannel();

        ByteBuffer byteBuffer = ByteBuffer.allocate(100);
        ByteBuffer byteBuffer1 = ByteBuffer.allocate(100);

        long read = channel.read(new ByteBuffer[]{byteBuffer, byteBuffer1});
        while (read != -1){
            byteBuffer.flip();
            byteBuffer1.flip();
            System.out.println(new String(byteBuffer.array(),StandardCharsets.UTF_8));
            System.out.println(new String(byteBuffer1.array(),StandardCharsets.UTF_8));

            byteBuffer.clear();
            byteBuffer1.clear();
            read = channel.read(new ByteBuffer[]{byteBuffer, byteBuffer1});
        }
    }

    @Test
    public void transferFrom() throws IOException {
        RandomAccessFile fromFile =
                new RandomAccessFile("C:\\Users\\john\\Desktop\\二代征信数据.sql", "rw");
        FileChannel fromFileChannel = fromFile.getChannel();

        RandomAccessFile toFile =
                new RandomAccessFile("C:\\Users\\john\\Desktop\\nio.txt", "rw");
        FileChannel toFileChannel = toFile.getChannel();

        toFileChannel.transferFrom(fromFileChannel,0,fromFileChannel.size());

        fromFileChannel.close();
        toFileChannel.close();
    }
}
