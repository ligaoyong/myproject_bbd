package com.bbd;

import com.spring5.web.dispatcherServlet.Annotations;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;

@ContextConfiguration
@WebAppConfiguration
public class MyTest {

    @Autowired
    private Annotations annotations;

    @Test
    public void test1(){
        annotations.getAccount();
    }

}
