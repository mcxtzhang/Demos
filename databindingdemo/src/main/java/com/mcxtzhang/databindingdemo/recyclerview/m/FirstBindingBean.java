package com.mcxtzhang.databindingdemo.recyclerview.m;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import static android.databinding.tool.util.GenerationalClassUtil.ExtensionFilter.BR;

/**
 * 介绍：
 * 作者：zhangxutong
 * 邮箱：mcxtzhang@163.com
 * CSDN：http://blog.csdn.net/zxt0601
 * 时间： 16/09/25.
 */

public class FirstBindingBean extends BaseObservable {
    private String url;
    private String name;

    public FirstBindingBean(String url) {
        this.url = url;
    }

    public FirstBindingBean(String url, String name) {
        this.url = url;
        this.name = name;
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
