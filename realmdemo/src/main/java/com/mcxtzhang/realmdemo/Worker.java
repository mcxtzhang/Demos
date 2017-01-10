package com.mcxtzhang.realmdemo;

import io.realm.RealmObject;

/**
 * 介绍：
 * 作者：zhangxutong
 * 邮箱：mcxtzhang@163.com
 * 主页：http://blog.csdn.net/zxt0601
 * 时间： 17/01/10.
 */

public class Worker extends RealmObject {
    private String name;
    private int age;

    public String getName() {
        return name;
    }

    public Worker setName(String name) {
        this.name = name;
        return this;
    }

    public int getAge() {
        return age;
    }

    public Worker setAge(int age) {
        this.age = age;
        return this;
    }

    @Override
    public String toString() {
        return "Worker{" +
                "age=" + age +
                ", name='" + name + '\'' +
                '}';
    }
}
