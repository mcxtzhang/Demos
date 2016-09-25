package com.mcxtzhang.databindingdemo.recyclerview.m;

import android.databinding.Bindable;

import com.mcxtzhang.databindingdemo.recyclerview.multype.MulTypeBean;

/**
 * 介绍：
 * 作者：zhangxutong
 * 邮箱：mcxtzhang@163.com
 * CSDN：http://blog.csdn.net/zxt0601
 * 时间： 16/09/25.
 */

public class FirstBindingBean extends MulTypeBean {
    private String url;
    private String name;

    public FirstBindingBean(String url) {
        this.url = url;
    }

    public FirstBindingBean(String url, String name) {
        this.url = url;
        this.name = name;
    }

    public FirstBindingBean(String url, int role) {
        this.url = url;
        setRole(role);
    }

    public FirstBindingBean(String url, String name, int role) {
        this.url = url;
        this.name = name;
        setRole(role);
    }

    @Bindable
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
        notifyPropertyChanged(com.mcxtzhang.databindingdemo.BR.url);
    }

    @Bindable
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        notifyPropertyChanged(com.mcxtzhang.databindingdemo.BR.name);
    }
}
