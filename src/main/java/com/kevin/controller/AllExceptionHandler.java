package com.kevin.controller;

import com.kevin.Exception.BaseException;
import com.kevin.Exception.HttpException;
import com.kevin.Exception.ServiceException;
import com.kevin.infrastructure.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@ControllerAdvice
public class AllExceptionHandler {
    Logger logger= LoggerFactory.getLogger(getClass());

    @ExceptionHandler(value = {HttpException.class, ServiceException.class})
    @ResponseBody
    public Result<?> jsonExceptionHandler(HttpServletRequest request, HttpServletResponse httpResponse, Exception e) {

        if( e  instanceof HttpException) {
            logger.warn("http exception",e);
            HttpException exception=(HttpException)e;
            httpResponse.setStatus(exception.getHttpcode());
            return Result.errorrResult(exception.getCode(),exception.getMessage());
        }

        if( e instanceof  ServiceException) {
            logger.warn("service exception",e);
            ServiceException exception = (ServiceException) e;
            return Result.errorrResult(exception.getCode(),exception.getMessage());
        }
        return Result.errorresult;

    }




    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseBody
    public Result<?> methodArgumentNotValidExceptionHandler(HttpServletRequest request, MethodArgumentNotValidException e) {


        logger.warn("参数校验失败",e);
        StringBuilder builder=new StringBuilder();
        e.getBindingResult().getAllErrors().forEach((l)->{
            builder.append(l.getDefaultMessage()+",");
        });
        return Result.errorrResult(builder.toString());

    }

    @ExceptionHandler(value = BaseException.class)
    @ResponseBody
    public Result<?> baseExceptionHandler(HttpServletRequest request, BaseException e){
        logger.warn("base exception",e);

        return  Result.errorrResult(e.getCode(),e.getMessage());
    }

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Result<?> exceptionHandler(HttpServletRequest request, Exception e) {


        logger.warn("系统异常",e);
        return Result.errorrResult("系统异常");

    }
}
