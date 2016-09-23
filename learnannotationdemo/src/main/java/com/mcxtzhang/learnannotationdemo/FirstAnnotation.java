package com.mcxtzhang.learnannotationdemo;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 介绍：第一个注解
 * 变量  方法 类 都可以用
 * 运行时可以见到
 * <p>
 * 作者：zhangxutong
 * 邮箱：zhangxutong@imcoming.com
 * 时间： 2016/9/23.
 */

@Target({ElementType.FIELD, ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface FirstAnnotation {
    String value() default "";

    /**
     * 是否输出，默认true
     * @return
     */
    boolean isShow() default true;
}
