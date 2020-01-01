package com.guava.io;

import com.google.common.base.Charsets;
import com.google.common.io.*;
import org.junit.Test;
import java.io.*;
import java.net.URL;

/**
 * Created by wuyongchong on 2018/11/6.
 * IO工具
 */
public class IOTest {
    /**
     * Guava使用术语"Stream”来表示在底层资源中具有位置状态的I/O数据的可关闭流。
     * 术语"byte Stream”指的是InputStream或OutputStream，
     * 而"char stream”指的是Reader或Writer(尽管它们的超类型Readable和Appendable的经常被用作方法参数类型)。
     * 相应的实用程序分为实用程序类ByteStreams和CharStreams。
     */
    /**
     * 大多数guava流相关的utils一次处理整个流/自己处理缓冲以提高效率。
     * 还要注意，接受流的Guava方法不会关闭流:关闭流通常是打开流的代码的责任。
     */
    /**
     * ByteStreams	                            CharStreams
     * byte[] toByteArray(InputStream)	        String toString(Readable)
     * N/TestStaticInit	                                    List<String> readLines(Readable)
     * long copy(InputStream, OutputStream)	    long copy(Readable, Appendable)
     * void readFully(InputStream, byte[])	    N/TestStaticInit
     * void skipFully(InputStream, long)	    void skipFully(Reader, long)
     * OutputStream nullOutputStream()	        Writer nullWriter()
     */
    @Test public void test1() throws IOException {
        //参数还是需要InputStream和Reader 不好用
        byte[] bytes = ByteStreams.toByteArray(new FileInputStream("E:\\aa.txt"));
    }

    /**
     * Files 操作文件的实用工具  确实很实用
     * Resources 操作网络IO的实用工具   非常实用
     * MoreFiles 操作目录的工具
     */
    @Test public void test2() throws IOException {
        Files.copy(null, (File) null);
        Files.readLines(null, Charsets.UTF_8);
        //Files.write(new byte[], File.listRoots());
        Resources.copy(new URL(null),new FileOutputStream(""));

    }

    /**
     * Sources and sinks ----> 对IO的统一抽象
     *
     * source或sink是一种资源，您知道如何打开一个新的流，例如文件或URL。
     * source是可读的，sink是可写的。此外，根据处理byte数据还是char数据，可以分解source和sink。
     * source：ByteSource CharSource
     * sink：ByteSink CharSink
     *
     * 这些api的优点是提供了一组通用的操作。
     */

    /**
     * 创建source和sink
     * 常用操作
     */
    @Test public void test3() throws IOException {
        /**
         * 通过Files MoreFiles Resources ByteSource CharSource ByteSink等创建Source
         */
        ByteSink byteSink = Files.asByteSink(null);
        ByteSource byteSource = Files.asByteSource(null);
        //MoreFiles.asCharSource(null);
        byteSource.copyTo(byteSink);
        byteSource.read();
        byteSink.writeFrom(null);
        byteSink.write(null);
    }

    /**
     * 关闭资源优选：try-with-resources
     * 这样就不用手动关闭资源了
     */
    @Test public void test4() throws IOException {
        try(FileInputStream inputStream = new FileInputStream(new File("G:\\a.txt"));
            FileOutputStream outputStream = new FileOutputStream(new File(""))){

            outputStream.write(inputStream.read());
        }
    }
}
