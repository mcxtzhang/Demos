package com.example;

/**
 * 介绍：
 * 作者：zhangxutong
 * 邮箱：zhangxutong@imcoming.com
 * 时间： 2016/9/23.
 */

public class TestFirstAnnotationBean {
    @FirstAnnotation
    private String exome;

    public TestFirstAnnotationBean(String exome) {
        this.exome = exome;
    }

    public String getExome() {
        return exome;
    }

    public void setExome(String exome) {
        this.exome = exome;
    }
}
