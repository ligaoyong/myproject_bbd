package com.spring5.Aop.log;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import java.lang.reflect.Method;

/**
 * 处理注解@Log的方面
 */
@Aspect
@Component
public class AnnotationLogAspect {
    //声明切入点annotationLogPointcut：com.spring5.Aop.log包下标注有@Log注解的任何方法
    @Pointcut("execution(* com.spring5.Aop.log..*(..)) && @annotation(com.spring5.Aop.log.Log) && bean(test)")
    public void annotationLogPointcut() {
    }


    /**
     * 对annotationLogPointcut切入点的环绕通知
     *
     * @return
     */
    @Around(value = "annotationLogPointcut()")
    public Object pringLog(ProceedingJoinPoint joinPoint) throws Throwable {
        //拿到连接点的签名
        Signature signature = joinPoint.getSignature();
        //将连接点的签名强制转换为方法签名（java的Aop连接点只能是方法）
        MethodSignature methodSignature = (MethodSignature) signature;
        //方法
        Method method = methodSignature.getMethod();
        //方法名称
        String name = method.getName();
        //方法上的@Log注解
        //method.setAccessible(true);
        Log log = method.getAnnotation(Log.class);
        //拿到注解中的值
        String msg = log.msg();

        System.out.println("进入方法：" + name);
        System.out.println("log：" + msg);
        //执行方法
        Object returnVal = joinPoint.proceed();
        System.out.println("退出方法：" + name);
        return returnVal;
    }

    @Before(value = "annotationLogPointcut()")
    public void printHello(){
        System.out.println("执行before增强");
    }
}
