package com.mcxtzhang.coordinatordemo;

/**
 * 介绍：
 * 作者：zhangxutong
 * 邮箱：zhangxutong@imcoming.com
 * 时间： 2016/9/30.
 */

public class TestBean {
    private String url;
    private String name;

    public String getUrl() {
        return url;
    }

    public TestBean setUrl(String url) {
        this.url = url;
        return this;
    }

    public String getName() {
        return name;
    }

    public TestBean setName(String name) {
        this.name = name;
        return this;
    }

    public TestBean(String url, String name) {
        this.url = url;
        this.name = name;
    }
}
