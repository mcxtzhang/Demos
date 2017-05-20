package com.mcxtzhang.databindingdemo.autobinding;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.mcxtzhang.databindingdemo.BR;

/**
 * Intro:
 * Author: zhangxutong
 * E-mail: mcxtzhang@163.com
 * Home Page: http://blog.csdn.net/zxt0601
 * Created:   2017/5/20.
 * History:
 */

public class ShopCartBean extends BaseObservable {
    int num;

    private ShopCartBean() {

    }

    private static class InnerHolder {
        private static final ShopCartBean INSTANCE = new ShopCartBean();
    }

    public static ShopCartBean getInstance() {
        return InnerHolder.INSTANCE;
    }

    @Bindable
    public String getNum() {
        return num + "";
    }


    public ShopCartBean setNum(int num) {
        this.num = num;
        notifyPropertyChanged(BR.num);
        return this;
    }
}
