package com.mcxtzhang.databindingdemo.flowgroup;

import android.view.View;
import android.widget.Toast;

/**
 * 介绍：
 * 作者：zhangxutong
 * 邮箱：zhangxutong@imcoming.com
 * 时间： 2016/10/10.
 */

public class FlowBean {
    String name;
    boolean show = true;

    public boolean isShow() {
        return show;
    }

    public FlowBean setShow(boolean show) {
        this.show = show;
        return this;
    }

    public FlowBean(String s, String s2) {
        name = s;
    }

    public String getName() {
        return name;
    }

    public FlowBean setName(String name) {
        this.name = name;
        return this;
    }

    public void onItemClick(View v) {
        Toast.makeText(v.getContext(), "ddd:" + name, Toast.LENGTH_SHORT).show();
    }
}
