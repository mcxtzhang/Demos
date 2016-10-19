package com.mcxtzhang.databindingdemo.mullayout;

/**
 * 介绍：
 * 作者：zhangxutong
 * 邮箱：zhangxutong@imcoming.com
 * 时间： 2016/10/19.
 */

public class MulLayoutBean2 {
    private String txt;
    private int counts;

    public MulLayoutBean2(String txt, int counts) {
        this.txt = txt;
        this.counts = counts;
    }

    public String getTxt() {
        return txt;
    }

    public MulLayoutBean2 setTxt(String txt) {
        this.txt = txt;
        return this;
    }

    public int getCounts() {
        return counts;
    }

    public MulLayoutBean2 setCounts(int counts) {
        this.counts = counts;
        return this;
    }
}
