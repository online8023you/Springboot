package com.demo.aop;

import com.alibaba.fastjson.JSON;
import com.demo.entity.SystemLog;
import com.demo.service.SystemLogService;
import org.apache.shiro.SecurityUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;

/**
 * @author Dawn
 * @ClassName com.demo.aop.SystemLogAspect
 * @Description
 * @date 2019/9/17 16:13
 */

@Aspect
@Component
public class SystemLogAspect {

    @Autowired
    private SystemLogService systemLogService;

    //定义切点 @Pointcut
    //在注解的位置切入代码
    @Pointcut("@annotation(com.demo.aop.MyLogPast)")
    public void logPointCut() {

    }

    @AfterReturning("logPointCut()")
    public void saveSystemLog(JoinPoint joinPoint) {
        //保存日志
        SystemLog systemLog = new SystemLog();
        MethodSignature signature = (MethodSignature)joinPoint.getSignature();
        //获取切入点所在的方法
        Method method = signature.getMethod();
        //获取操作
        MyLogPast myLogPast = method.getAnnotation(MyLogPast.class);
        if (myLogPast !=null){
            String value = myLogPast.value();
            //保存获取的操作
            systemLog.setOperation(value);
        }
        //获取请求的类名
        String className = joinPoint.getTarget().getClass().getName();
        //获取请求的方法
        String methodName = method.getName();
        systemLog.setMethod(className+"."+methodName);
        //获取请求的参数
        Object[] args = joinPoint.getArgs();
        System.out.println(args);
        //将参数所在的数组转换成json
        String params = JSON.toJSONString(args);
        systemLog.setParams(params);
        systemLog.setCreate_time(new Date());
        //获取用户名
        String userName = (String) SecurityUtils.getSubject().getPrincipal();
        systemLog.setUsername(userName);
        //获取用户ip地址
        HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
        systemLog.setIp(IPUtils.getIpAddr(request));

        //调用service保存SysLog实体类到数据库
        systemLogService.insertSystemLog(systemLog);
    }

}
