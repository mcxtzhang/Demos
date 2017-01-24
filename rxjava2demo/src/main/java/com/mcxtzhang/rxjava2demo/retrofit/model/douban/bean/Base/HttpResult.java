package com.mcxtzhang.rxjava2demo.retrofit.model.douban.bean.Base;

import com.google.gson.annotations.SerializedName;

/**
 * 介绍：
 * 作者：zhangxutong
 * 邮箱：mcxtzhang@163.com
 * 主页：http://blog.csdn.net/zxt0601
 * 时间： 2017/1/5.
 */

public class HttpResult<T> {
    //用来模仿resultCode和resultMessage
    private int count;
    private int start;
    private int total;
    private String title;

    //用来模仿Data
    @SerializedName("subjects")
    private T data;

    public int getCount() {
        return count;
    }

    public HttpResult setCount(int count) {
        this.count = count;
        return this;
    }

    public int getStart() {
        return start;
    }

    public HttpResult setStart(int start) {
        this.start = start;
        return this;
    }

    public int getTotal() {
        return total;
    }

    public HttpResult setTotal(int total) {
        this.total = total;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public HttpResult setTitle(String title) {
        this.title = title;
        return this;
    }

    public T getData() {
        return data;
    }

    public HttpResult setData(T data) {
        this.data = data;
        return this;
    }
}
