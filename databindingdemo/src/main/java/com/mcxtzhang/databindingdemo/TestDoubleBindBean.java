package com.mcxtzhang.databindingdemo;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

/**
 * 介绍：测试双向绑定
 * 作者：zhangxutong
 * 邮箱：zhangxutong@imcoming.com
 * 时间： 2016/10/25.
 */

public class TestDoubleBindBean extends BaseObservable {
    private boolean select;

    @Bindable
    public boolean isSelect() {
        return select;
    }

    public TestDoubleBindBean setSelect(boolean select) {
        this.select = select;
        notifyPropertyChanged(BR.select);
        return this;
    }
}
