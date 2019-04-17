package com.serialization;

import com.caucho.hessian.io.Hessian2Input;
import com.caucho.hessian.io.Hessian2Output;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Hessian序列化方式
 */
public class Hessian {

    static byte[] global_bytes;

    /**
     * Hessian序列化
     * @throws IOException
     */
    @Test
    public void hessian() throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        Hessian2Output hessian2Output = new Hessian2Output(byteArrayOutputStream);
        hessian2Output.writeObject(new JdkSerial.Persion("张三",28));
        hessian2Output.flush();
        byte[] bytes = byteArrayOutputStream.toByteArray();
        global_bytes = bytes;
        System.out.println(bytes);
        reHessian();
    }

    public void reHessian() throws IOException {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(global_bytes);
        Hessian2Input hessian2Input = new Hessian2Input(byteArrayInputStream);
        Object o = hessian2Input.readObject(JdkSerial.Persion.class);
        System.out.println(o);
    }

}
