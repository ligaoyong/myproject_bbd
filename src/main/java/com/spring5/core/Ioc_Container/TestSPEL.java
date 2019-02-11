package com.spring5.core.Ioc_Container;

import org.junit.Test;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
public class TestSPEL {

    @Test
    public void test1(){
        ExpressionParser parser = new SpelExpressionParser();
        Expression expression = parser.parseExpression("'hello'.concat(' world!')");
        System.out.println(expression.getValue());
    }

}
