package com.example;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Intro:  Use in field, key() means key in bundle.
 * Author: zhangxutong
 * E-mail: mcxtzhang@163.com
 * Home Page: http://blog.csdn.net/zxt0601
 * Created:   2017/2/4.
 * History:
 */

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.CLASS)
public @interface ZParams {
    String key();
}
