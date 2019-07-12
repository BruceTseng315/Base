package com.kevin.controller;

import com.kevin.Exception.BaseException;
import com.kevin.Exception.HttpException;
import com.kevin.infrastructure.Result;
import com.kevin.model.User;
import com.kevin.model.request.AntitrashRequest;
import com.kevin.model.request.GetUser;
import com.kevin.service.TestService;
import com.kevin.util.HttpClientUtils;
import com.xiaomi.ai.antitrash.control.MainControl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: kevin
 * Date: 2019-05-09
 * Time: 14:10
 */
@Api(value = "Test", tags = "For test", description = "for test")
@RestController
public class TestController {
    private Logger logger = LoggerFactory.getLogger(TestController.class);
    @Autowired
    TestService testService;

    @GetMapping("/test")
    @ApiOperation(value = "test hello",notes = "for test")
    public Result<String> hello(){
        byte a = 1;
        return Result.success("Hello, world!");
    }

    @GetMapping("/test/getUser")
    @ApiOperation(value = "get user",notes = "get user")
    public Result<User> getUser(@RequestParam(value = "name") String name){
        logger.debug("request :{}",name);
        return Result.success(new User());
    }

    @PostMapping("/test/getUser")
    @ApiOperation(value = "update user",notes = "update user")
    public Result<User> updateUser(@RequestBody GetUser getUser){
        logger.debug("request :{}",getUser);
        return Result.success(new User());
    }

    @GetMapping("/async")
    public Result<String> asy(){
        testService.test();
        return Result.success("asc");
    }

    @GetMapping("/test/exception/http")
    public void throwHttpException()throws HttpException{
        throw new HttpException(500,"you got a httpException");
    }

    @GetMapping("/test/exception/base")
    public void throwBaseException()throws BaseException{
        throw new BaseException(500,"you got a baseException");
    }

    // 黄反
    @PostMapping("/test/antitrash")
    public Result antitrash(@RequestBody AntitrashRequest request) {
        boolean res = MainControl.process(request.getQuery(), request.getDomain(), String.valueOf(request.getFlag()));

        return Result.success(res);
    }

    // 增加密令
    @GetMapping("/test/addLoveKeys")
    public Result<String> addKeys(@RequestParam("num") Integer num, @RequestParam("len") Integer len, @RequestParam("env") String env) throws Exception{
        String url = "api/skillstore/v2/assistant/visitors/noauth/addLoveKeysBatch";
        String host = "https://preview.i.ai.mi.com/";
        int sum = num;
        while(sum>1000){
            Map<String,String> querys = new HashMap<>();
            querys.put("env",env);
            querys.put("len",String.valueOf(len));
            querys.put("amount",String.valueOf(1000));
            getRes(host,url,querys);
            sum = sum - 1000;
            Thread.sleep(1000);
        }
        Map<String,String> querys = new HashMap<>();
        querys.put("env",env);
        querys.put("len",String.valueOf(len));
        querys.put("amount",String.valueOf(sum));
        String res = getRes(host,url,querys);
        return Result.success(res);
    }

    private String getRes(String host, String url, Map<String,String> querys) throws Exception{
        HttpResponse response=null;
        HttpEntity entity=null;
        String respContent=null;
        response = HttpClientUtils.doGet(host, url,new HashMap<>(), querys);
        entity=response.getEntity();
        respContent= EntityUtils.toString(entity, "UTF-8");

        return respContent;

    }
}
