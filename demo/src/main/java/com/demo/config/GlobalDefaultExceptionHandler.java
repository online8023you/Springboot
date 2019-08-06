package com.demo.config;

import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.UnauthenticatedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Dawn
 * @ClassName com.demo.config.GlobalDefaultExceptionHandler
 * @Description
 * @date 2019/7/16 16:27
 */

@ControllerAdvice
public class GlobalDefaultExceptionHandler {

    @ExceptionHandler(AuthorizationException.class)
    @ResponseBody
    public String defaultExceptionHandler(HttpServletRequest req, Exception e){
        return "对不起，你没有访问权限！";
    }
}
