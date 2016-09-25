package com.mcxtzhang.databindingdemo;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

/**
 * 介绍：
 * 作者：zhangxutong
 * 邮箱：zhangxutong@imcoming.com
 * 时间： 2016/9/23.
 */

public class TestBean extends BaseObservable {
    public static final String EMPTY_VALUE = "这里空了？？";
    private int id;
    private String name;

    public TestBean(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Bindable
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        notifyPropertyChanged(BR.name);
    }
}
