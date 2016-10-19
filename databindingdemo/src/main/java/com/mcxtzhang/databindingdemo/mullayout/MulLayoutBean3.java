package com.mcxtzhang.databindingdemo.mullayout;

/**
 * 介绍：
 * 作者：zhangxutong
 * 邮箱：zhangxutong@imcoming.com
 * 时间： 2016/10/19.
 */

public class MulLayoutBean3 {
    private String hint;
    private MulLayoutBean1 mBean1;

    public String getHint() {
        return hint;
    }

    public MulLayoutBean3 setHint(String hint) {
        this.hint = hint;
        return this;
    }

    public MulLayoutBean1 getBean1() {
        return mBean1;
    }

    public MulLayoutBean3 setBean1(MulLayoutBean1 bean1) {
        mBean1 = bean1;
        return this;
    }
}
