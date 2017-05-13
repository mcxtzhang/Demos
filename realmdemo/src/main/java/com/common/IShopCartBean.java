package com.common;

/**
 * Intro:
 * Author: zhangxutong
 * E-mail: mcxtzhang@163.com
 * Home Page: http://blog.csdn.net/zxt0601
 * Created:   2017/5/12.
 * History:
 */
public interface IShopCartBean {

    int count();

    String name();

    String price();

    String id();

    String group();//tag

    long updateTime();


}
