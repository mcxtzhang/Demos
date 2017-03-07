package com.mcxtzhang.daggerdemo.chap3;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Qualifier;

/**
 * Intro:只有相同的@Named的@Inject成员变量与@Provides方法才可以被对应起来。
 * 更常用的方法是使用注解@Qualifier来自定义注解。
 * Author: zhangxutong
 * E-mail: mcxtzhang@163.com
 * Home Page: http://blog.csdn.net/zxt0601
 * Created:   2017/3/7.
 * History:
 */

@Qualifier
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface WhichView {
    String value() default "filled";
}
