package com.mcxtzhang.databindingdemo.mullayout;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.mcxtzhang.databindingdemo.BR;

/**
 * 介绍：
 * 作者：zhangxutong
 * 邮箱：zhangxutong@imcoming.com
 * 时间： 2016/10/19.
 */

public class MulLayoutBean1 extends BaseObservable {
    private String name;
    private int count;

    public MulLayoutBean1(String name, int count) {
        this.name = name;
        this.count = count;
    }

    public String getName() {
        return name;
    }

    public MulLayoutBean1 setName(String name) {
        this.name = name;
        return this;
    }

    @Bindable
    public int getCount() {
        return count;
    }

    public MulLayoutBean1 setCount(int count) {
        this.count = count;
        notifyPropertyChanged(BR.count);
        return this;
    }
}
