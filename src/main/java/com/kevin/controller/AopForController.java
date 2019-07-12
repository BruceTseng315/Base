package com.kevin.controller;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.lang.reflect.Method;

@Aspect
@Component
public class AopForController {
    Logger logger= LoggerFactory.getLogger(AopForController.class);


    @Around("execution(* com.kevin.controller..*(..)) && "
            + "("
            +"@annotation(org.springframework.web.bind.annotation.RequestMapping)||"
            +"@annotation(org.springframework.web.bind.annotation.GetMapping)||"
            +"@annotation(org.springframework.web.bind.annotation.PostMapping)"
            + ")")
    public Object  around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Signature signature=proceedingJoinPoint.getSignature();
        System.out.println("logger enable is: " + logger.isDebugEnabled());
        if(logger.isDebugEnabled() &&
                signature instanceof MethodSignature){
            MethodSignature methodSignature=(MethodSignature)signature;
            logger.debug("请求地址:"+getRequestPath(methodSignature.getMethod()));
            Object[] args=proceedingJoinPoint.getArgs();
            logger.debug("请求参数：[");
            for(int i=0;i<args.length;i++){
                if(args[i]!=null) {
                    logger.debug(args[i].toString());
                }
            }
            logger.debug("]");
        }
        long start = System.currentTimeMillis();
        Object res = proceedingJoinPoint.proceed();
        long end = System.currentTimeMillis();
        logger.debug("result is {}", res);
        logger.debug("cost: " + (end-start) + " ms");
        return res;

    }

    /**
     * 获取请求地址
     * @param method
     * @return
     */
    String getRequestPath(Method method) {

        String[] valuepaths = null;
        RequestMapping requestMapping = method.getAnnotation(org.springframework.web.bind.annotation.RequestMapping.class);
        if (requestMapping != null) {
            valuepaths = requestMapping.value();
            if (valuepaths != null && valuepaths.length > 0) {
                return valuepaths[0];
            }
        }


        GetMapping getMapping = method.getAnnotation(org.springframework.web.bind.annotation.GetMapping.class);
        if (getMapping != null) {
            valuepaths = getMapping.value();
            if (valuepaths != null && valuepaths.length > 0) {
                return valuepaths[0];
            }
        }


        PostMapping postMapping = method.getAnnotation(org.springframework.web.bind.annotation.PostMapping.class);
        if (postMapping != null) {
            valuepaths = postMapping.value();
            if (valuepaths != null && valuepaths.length > 0) {
                return valuepaths[0];
            }
        }

        return null;
    }
}
