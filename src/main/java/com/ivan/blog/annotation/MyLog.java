package com.ivan.blog.annotation;

import java.lang.annotation.*;

/**
 *  @Author: Ivan
 *  @Description:   日志操作
 *  @Date: 2019/11/14 09:42
 */
@Target(ElementType.METHOD)             //注解放置的目标位置,METHOD是可注解在方法级别上
@Retention(RetentionPolicy.RUNTIME)     //注解在哪个阶段执行
@Documented                             //生成文档
public @interface MyLog {
    String value() default "";
}
