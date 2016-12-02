package com.mcxtzhang.rxjavademo.network;

/**
 * 介绍：支持提交干货到审核区:
 * 作者：zhangxutong
 * 邮箱：mcxtzhang@163.com
 * 主页：http://blog.csdn.net/zxt0601
 * 时间： 2016/12/2.
 */

public class Add2GankBean {
    private String url;
    private String desc;
    private String who;
    private String type;
    private boolean debug;

    public String getUrl() {
        return url;
    }

    public Add2GankBean setUrl(String url) {
        this.url = url;
        return this;
    }

    public String getDesc() {
        return desc;
    }

    public Add2GankBean setDesc(String desc) {
        this.desc = desc;
        return this;
    }

    public String getWho() {
        return who;
    }

    public Add2GankBean setWho(String who) {
        this.who = who;
        return this;
    }

    public String getType() {
        return type;
    }

    public Add2GankBean setType(String type) {
        this.type = type;
        return this;
    }

    public boolean isDebug() {
        return debug;
    }

    public Add2GankBean setDebug(boolean debug) {
        this.debug = debug;
        return this;
    }
}
