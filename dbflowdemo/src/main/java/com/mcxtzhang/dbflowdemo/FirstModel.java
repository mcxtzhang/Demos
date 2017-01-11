package com.mcxtzhang.dbflowdemo;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

/**
 * 介绍：
 * 作者：zhangxutong
 * 邮箱：mcxtzhang@163.com
 * 主页：http://blog.csdn.net/zxt0601
 * 时间： 17/01/11.
 */

@Table(database = MyDatabase.class)
public class FirstModel extends BaseModel {

    @PrimaryKey
    @Column
    private long myId;

    @Column
    private String name;


    public long getMyId() {
        return myId;
    }

    public FirstModel setMyId(long myId) {
        this.myId = myId;
        return this;
    }

    public String getName() {
        return name;
    }

    public FirstModel setName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public String toString() {
        return "FirstModel{" +
                "myId=" + myId +
                ", name='" + name + '\'' +
                '}';
    }
}
