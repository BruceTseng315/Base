package com.kevin.controller;

import com.kevin.infrastructure.Result;
import com.kevin.model.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(value = "User", tags = "User", description = "User")
@RequestMapping("/user")
public class UserController {
    private Logger logger = LoggerFactory.getLogger(TestController.class);

    @GetMapping("/getUser")
    @ApiOperation(value = "get user",notes = "get user")
    public Result<User> getUser(@RequestParam(value = "name") String name){
        logger.debug("request :{}",name);
        return Result.success(new User());
    }
}
