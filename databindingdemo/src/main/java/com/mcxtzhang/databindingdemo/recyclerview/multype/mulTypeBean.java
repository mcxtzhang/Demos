package com.mcxtzhang.databindingdemo.recyclerview.multype;

import android.databinding.BaseObservable;

/**
 * 介绍：
 * 作者：zhangxutong
 * 邮箱：mcxtzhang@163.com
 * CSDN：http://blog.csdn.net/zxt0601
 * 时间： 16/09/25.
 */

public class MulTypeBean extends BaseObservable {
    private int role;

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }
}
