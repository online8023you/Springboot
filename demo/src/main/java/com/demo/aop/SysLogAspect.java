package com.demo.aop;

import com.alibaba.fastjson.JSON;
import com.demo.entity.SysLogModel;
import com.demo.service.SysLogService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.TextNode;
import com.google.common.collect.Maps;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.util.JSONUtils;
import org.apache.shiro.SecurityUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.spring.web.json.Json;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.sound.midi.SysexMessage;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.Map.Entry;

/**
 * @author Dawn
 * @ClassName com.demo.aop.SysLogAspect
 * @Description
 * @date 2019/9/18 10:51
 */

@Aspect
@Component
@Slf4j
public class SysLogAspect {


    @Autowired
    private SysLogService sysLogService;



    /**
     * 正常返回处理
     *
     * @param joinPoint   连接点
     * @param sysLogPoint 注解
     * @param object      返回结果
     */
    @AfterReturning(value = "@annotation(sysLogPoint)", returning = "object")
    public void afterReturning(JoinPoint joinPoint, SysLogPoint sysLogPoint, Object object) throws NoSuchMethodException, ClassNotFoundException {
        SysLogModel sysLogModel = buildLog(joinPoint, sysLogPoint, object, null);
        saveLog(sysLogModel);

    }


    /**
     * 抛出异常的处理
     *
     * @param joinPoint   连接点
     * @param sysLogPoint 注解
     * @param exMsg       异常对象
     */
    @AfterThrowing(value = "@annotation(sysLogPoint)", throwing = "exMsg")
    public void afterThrowing(JoinPoint joinPoint, SysLogPoint sysLogPoint, Throwable exMsg) throws NoSuchMethodException, ClassNotFoundException {
        SysLogModel sysLogModel = buildLog(joinPoint, sysLogPoint, null, exMsg);
        saveLog(sysLogModel);
    }

    private void saveLog(SysLogModel sysLogModel) {

        // 本例中直接打印日志，生产环境中可采用异步的方式，保存到DB等媒介中
        log.info("[SysLog]: {}", JSON.toJSONString(sysLogModel));
        sysLogService.insertSysLogModel(sysLogModel);

    }

    /**
     * 构建日志对象
     *
     * @param joinPoint   连接点
     * @param sysLogPoint 注解
     * @param object      处理结果对象
     * @param exMsg       处理异常对象
     * @return 日志对象
     */
    private SysLogModel buildLog(JoinPoint joinPoint, SysLogPoint sysLogPoint, Object object, Throwable exMsg) throws NoSuchMethodException, ClassNotFoundException {
        SysLogModel sysLogModel = new SysLogModel();
        sysLogModel.setActionName(sysLogPoint.actionName());
        sysLogModel.setTarget(sysLogPoint.sysLogTarget().name());

        //获取请求的参数
        sysLogModel.setParams(handleArgs(joinPoint));

        sysLogModel.setCreate_time(new Date());

        //获取返回值

        //
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        //获取请求的类名
        String className = joinPoint.getTarget().getClass().getName();
        //获取请求的方法
        String methodName = method.getName();
        sysLogModel.setMethod(className + "." + methodName);

        /*sysLogModel.setInput(handleInput(joinPoint.getArgs(), Arrays.asList(sysLogPoint.sensitiveParams())));
        sysLogModel.setOutput(handleOutput(object,sysLogPoint.ignoreOutput()));*/
        sysLogModel.setExMsg(handleException(exMsg));
        //获取用户名
        String userName = (String) SecurityUtils.getSubject().getPrincipal();
        sysLogModel.setUsername(userName);
        //获取用户ip地址
        HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
        sysLogModel.setIp(IPUtils.getIpAddr(request));
        return sysLogModel;
    }

    private String handleArgs(JoinPoint joinPoint) throws ClassNotFoundException, NoSuchMethodException {
        String classType = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();
        Class<?>[] classes = new Class[args.length];
        for (int k = 0; k < args.length; k++) {
            // 对于接受参数中含有MultipartFile，ServletRequest，ServletResponse类型的特殊处理，我这里是直接返回了null。（如果不对这三种类型判断，会报异常）
            if (args[k] instanceof MultipartFile || args[k] instanceof ServletRequest || args[k] instanceof ServletResponse || args[k] instanceof MultipartFile[]) {
                return null;
            }else
            if (!args[k].getClass().isPrimitive()) {
                // 当方法参数是基础类型，但是获取到的是封装类型的就需要转化成基础类型
                // String result = args[k].getClass().getName();
                // Class s = map.get(result);

                // 当方法参数是封装类型
                Class s = args[k].getClass();

                classes[k] = s == null ? args[k].getClass() : s;
            }
            else {
                return null;
            }

        }
        ParameterNameDiscoverer pnd = new DefaultParameterNameDiscoverer();
        // 获取指定的方法，第二个参数可以不传，但是为了防止有重载的现象，还是需要传入参数的类型
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        // 参数名
        String[] parameterNames = pnd.getParameterNames(method);
        // 通过map封装参数和参数值
        HashMap<String, Object> paramMap = new HashMap();
        for (int i = 0; i < parameterNames.length; i++) {

            if (parameterNames[i].equals("password")){
                paramMap.put(parameterNames[i], "[hidden]");
            }else {
                paramMap.put(parameterNames[i], args[i]);
            }
        }
        return JSON.toJSONString(paramMap);
    }


    /**
     * 处理输入结果
     *
     * @param args            入参
     * @param sensitiveParams 敏感参数关键字
     * @return 处理后的入参
     */
    private String handleInput(Object[] args, List<String> sensitiveParams) {
        Map<String, Object> argMap = Maps.newTreeMap();
        ObjectMapper objectMapper = new ObjectMapper();
        if (!ObjectUtils.isEmpty(args)) {
            for (int i = 0; i < args.length; i++) {
                if (args[i] != null && !ObjectUtils.isEmpty(sensitiveParams)) {
                    try {
                        JsonNode root = objectMapper.readTree(JSON.toJSONString(args[i]));
                        handleSensitiveParams(root, sensitiveParams);
                        argMap.put("arg" + (i + 1), root);
                    } catch (IOException e) {
                        argMap.put("arg" + (i + 1), "[exception]");
                    }
                } else {
                    argMap.put("arg" + (i + 1), args[i]);
                }
            }
        }
        return JSON.toJSONString(argMap);
    }


    /**
     * 处理输出记过
     *
     * @param result 源输出结果
     * @param ignore 是否忽略结果
     * @return 处理后的输出结果
     */
    private String handleOutput(Object result, boolean ignore) {
        return (ignore || result == null) ? null : JSON.toJSONString(result);
    }

    /**
     * 处理异常信息
     *
     * @param exMsg 异常对象
     * @return 处理后的异常信息
     */
    private String handleException(Throwable exMsg) {
        return exMsg == null ? null : exMsg.toString();
    }

    /**
     * 处理敏感参数
     *
     * @param jsonNode jackson节点
     * @param params   敏感参数名列表
     */
    private void handleSensitiveParams(JsonNode jsonNode, List<String> params) {
        if (jsonNode.isObject()) {
            Iterator<Entry<String, JsonNode>> jsonNodeIterator = jsonNode.fields();
            while (jsonNodeIterator.hasNext()) {
                Entry<String, JsonNode> node = jsonNodeIterator.next();
                if (params.contains(node.getKey())) {
                    node.setValue(new TextNode("[hidden]"));
                } else {
                    JsonNode tmpNode = node.getValue();
                    if (tmpNode.isObject()) {

                        handleSensitiveParams(tmpNode, params);
                    } else if (tmpNode.isArray()) {
                        for (JsonNode tmpJsonNode : tmpNode
                        ) {
                            handleSensitiveParams(tmpJsonNode, params);
                        }
                    }
                }
            }
        } else if (jsonNode.isArray()) {
            for (JsonNode jsNode : jsonNode
            ) {
                handleSensitiveParams(jsNode, params);
            }
        }
    }
}
