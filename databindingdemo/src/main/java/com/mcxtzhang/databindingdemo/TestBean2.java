package com.mcxtzhang.databindingdemo;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

/**
 * 介绍：同名成员变量，BR里会不会冲突？
 * 作者：zhangxutong
 * 邮箱：mcxtzhang@163.com
 * CSDN：http://blog.csdn.net/zxt0601
 * 时间： 16/09/24.
 */

public class TestBean2 extends BaseObservable {

    public String name;

    private TestBean testBean;

    @Bindable
    public TestBean getTestBean() {
        return testBean;
    }

    public void setTestBean(TestBean testBean) {
        this.testBean = testBean;
        notifyPropertyChanged(BR.testBean);
    }

    public TestBean2(String name) {
        this.name = name;
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
