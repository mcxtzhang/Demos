package com.mcxtzhang.dbflowdemo;

import com.raizlabs.android.dbflow.annotation.Database;

/**
 * 介绍：
 * 作者：zhangxutong
 * 邮箱：mcxtzhang@163.com
 * 主页：http://blog.csdn.net/zxt0601
 * 时间： 17/01/11.
 */

@Database(name = MyDatabase.NAME, version = MyDatabase.VERSION)
public class MyDatabase {
    //数据库名称
    public static final String NAME = "mcxtzang_DBFlowDatabase";
    //数据库版本号
    public static final int VERSION = 1;
}
