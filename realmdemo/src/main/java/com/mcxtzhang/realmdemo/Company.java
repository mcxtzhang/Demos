package com.mcxtzhang.realmdemo;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * 介绍：
 * 作者：zhangxutong
 * 邮箱：mcxtzhang@163.com
 * 主页：http://blog.csdn.net/zxt0601
 * 时间： 17/01/10.
 */

public class Company extends RealmObject {
    @PrimaryKey
    private long id;

    private String name;

    private RealmList<Worker> workers;

    public long getId() {
        return id;
    }

    public Company setId(long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Company setName(String name) {
        this.name = name;
        return this;
    }

    public RealmList<Worker> getWorkers() {
        return workers;
    }

    public Company setWorkers(RealmList<Worker> workers) {
        this.workers = workers;
        return this;
    }
}
