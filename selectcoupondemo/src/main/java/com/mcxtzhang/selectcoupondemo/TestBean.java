package com.mcxtzhang.selectcoupondemo;

/**
 * 介绍：
 * 作者：zhangxutong
 * 邮箱：zhangxutong@imcoming.com
 * 时间： 2016/9/29.
 */

public class TestBean extends SelectedBean {
    private String name;

    public TestBean(String name) {
        this.name = name;
    }

    public TestBean(String name,boolean isSelected) {
        this.name = name;
        setSelected(isSelected);
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
