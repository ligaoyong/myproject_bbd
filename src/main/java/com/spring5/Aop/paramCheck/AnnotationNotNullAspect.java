package com.spring5.Aop.paramCheck;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

/**
 * 处理注解@NotNull的方面
 */
@Aspect
@Component
public class AnnotationNotNullAspect {

    private Parameter parameter;

    //切点：paramCheck包及子包下的所有方法
    @Pointcut("within(com.spring5.Aop.paramCheck..*)")
    public void notNullPointcut(){}

    /**
     * 前置通知检查参数
     * 任何通知都可以将JointPoint作为第一个参数
     * 检查标注有@Notnull的参数是否为空
     */
    @Before("notNullPointcut()")
    public void checkParam(JoinPoint joinPoint) throws Exception {
        MethodSignature signature = (MethodSignature)joinPoint.getSignature();
        Method method = signature.getMethod();

        //在一个请求中 任何一处都能通过以下获取到请求对象
        //RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();

        Parameter[] parameters = method.getParameters();
        //获取方法上所有的参数值
        Object[] args = joinPoint.getArgs();

        for (int index=0;index<parameters.length;index++) {
            Parameter parameter = parameters[index];
            //如果参数上标注有@NotNull且参数值为null
            if (parameter.isAnnotationPresent(NotNull.class) && args[index] == null){
                throw new Exception("参数异常:"+parameter.getName()+" = "+null);
            }
        }
    }

}
