package com.demo.controller;

import com.demo.entity.Authority;
import com.demo.entity.Role;
import com.demo.entity.User;
import com.demo.service.AuthorityService;
import com.demo.service.RoleService;
import com.demo.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Api(tags = "界面跳转")
@Controller
public class ViewController {



    @Autowired
    AuthorityService authorityService;

    @RequestMapping(value = "/")
    @ApiOperation("登陆界面")
    public String login(){
        return "login";
    }


    @RequestMapping(value = "/homepage")
    @ApiOperation("主页")
    public  String authoritySystem(){
        return "homepage";
    }




}
