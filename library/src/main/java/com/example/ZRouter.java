package com.example;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Intro: Used in Class, to get path
 * Author: zhangxutong
 * E-mail: mcxtzhang@163.com
 * Home Page: http://blog.csdn.net/zxt0601
 * Created:   2017/1/17.
 * History:
 */

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.CLASS)
public @interface ZRouter {
    public String path() default "";
}
