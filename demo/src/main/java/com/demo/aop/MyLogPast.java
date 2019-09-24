package com.demo.aop;

import java.lang.annotation.*;

/**
 * @author Dawn
 * @ClassName com.demo.aop.MyLogPast
 * @Description
 * @date 2019/9/17 16:10
 */

@Target(ElementType.METHOD) //注解放置的目标位置,METHOD是可注解在方法级别上
@Retention(RetentionPolicy.RUNTIME) //注解在哪个阶段执行
@Documented //生成文档
public @interface MyLogPast {
    String value() default "";
}
