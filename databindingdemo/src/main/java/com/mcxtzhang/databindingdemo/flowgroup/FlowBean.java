package com.mcxtzhang.databindingdemo.flowgroup;

import java.util.ArrayList;
import java.util.List;

/**
 * 介绍：
 * 作者：zhangxutong
 * 邮箱：zhangxutong@imcoming.com
 * 时间： 2016/10/10.
 */

public class FlowBean {
    String name;
    boolean show = false;

    private List<String> list = new ArrayList<>();

    public List<String> getList() {
        if (list.size()<1){
            list.add(new String("ddd"));
        }
        return list;
    }

    public FlowBean setList(List<String> list) {
        this.list = list;
        return this;
    }

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

/*    public void onItemClick(View v) {
        Toast.makeText(v.getContext(), "ddd:" + name, Toast.LENGTH_SHORT).show();
    }*/
}
