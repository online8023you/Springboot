package com.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Dawn
 * @ClassName com.demo.controller.RedisSession
 * @Description
 * @date 2019/7/11 11:11
 */

@RestController
public class RedisSession {

    @GetMapping(value = "/first")
    public Map<String,Object> firstResp (HttpServletRequest request) {
        Map<String,Object> map = new HashMap<>();
        request.getSession().setAttribute("request Url",request.getRequestURL());
        map.put("request Url",request.getRequestURL());
        return map;
    }

    @GetMapping(value = "/sessions")
    public Object sessions(HttpServletRequest request){
        Map<String,Object> map = new HashMap<>();
        map.put("sessionId",request.getSession().getId());
        map.put("message",request.getSession().getAttribute("request Url"));
        return map;
    }
}
