package com.demo.aop;

import java.lang.annotation.*;

/**
 * @author Dawn
 * @ClassName com.demo.aop.SysLogPoint
 * @Description
 * @date 2019/9/18 10:17
 */

@Target(ElementType.METHOD) //注解放置的目标位置,METHOD是可注解在方法级别上
@Retention(RetentionPolicy.RUNTIME) //注解在哪个阶段执行
@Documented //生成文档
public @interface SysLogPoint {

    /**
     * 操作名:每一个操作，都需指定一个操作名
     */
    String actionName() default "unknown";


    /**
     * 是否忽略结果:true的情况下，将不记录目标处理的输出结果
     */
    boolean ignoreOutput() default false;

    /**
     * 敏感参数:像password这类参数在记录时，需要脱敏
     */
    String[] sensitiveParams() default {};

    /**
     *目标类型：CONTROLLER：controller日志, SERVICE：service日志, DAO：dao日志, METHOD：普通方法日志
     * 其中的SysLogTarget是一个枚举，在项目分层时，常常分为controller、service、dao等。
     * 记录不同层的日志，最后区别开来，更利于后期的日志分析。
     * 在日志开发中，往往需要记录controller层的操作日志，所以这里的target默认值为CONTROLLER
     */
    SysLogTarget sysLogTarget() default SysLogTarget.CONTROLLER;
}
