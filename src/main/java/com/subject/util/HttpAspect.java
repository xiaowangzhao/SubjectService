package com.subject.util;

import com.subject.Exception.ExceptionHandle;
import com.subject.dto.Result;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @author liangqin
 * @date 2019/5/7 17:50
 */

/**
 * 使用@Pointcut来定义切面所需要切入的位置，
 * 对每一个HTTP请求都需要切入，
 * 在进入方法之前使用@Before记录了调用的接口URL，
 * 调用的方法，调用方的IP地址以及输入的参数
 */
@Aspect
@Component
public class HttpAspect {

    private final static Logger logger = LoggerFactory.getLogger(ExceptionHandle.class);

    @Autowired
    private ExceptionHandle exceptionHandle;

    @Pointcut("execution(public * com.subject.controller.*.*(..))")
    public void log() {

    }

    @Before("log()")
    public void doBefore(JoinPoint joinPoint) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        //url
        logger.info("url={}", request.getRequestURI());

        //method
        logger.info("method={}", request.getMethod());

        //ip
        logger.info("ip={}", request.getRemoteAddr());

        //class_method
        logger.info("class_method={}", joinPoint.getSignature().getDeclaringType() + "," + joinPoint.getSignature().getName());

        //args[]
        logger.info("args={}", joinPoint.getArgs());
    }

    @Around("log()")
    public Object doAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable{
        Result result = null;

        try{

        }catch (Exception e) {
            return exceptionHandle.exceptionGet(e);
        }
        if(result == null) {
            return proceedingJoinPoint.proceed();
        }else {
            return  result;
        }
    }

    //打印输出结果
    @AfterReturning(pointcut = "log()", returning = "object")
    public void doAfterReturing(Object object) {
        logger.info("response={}", object.toString());
    }
}
