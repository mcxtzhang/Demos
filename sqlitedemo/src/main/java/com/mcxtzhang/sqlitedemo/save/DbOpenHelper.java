package com.mcxtzhang.sqlitedemo.save;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Intro:
 * Author: zhangxutong
 * E-mail: mcxtzhang@163.com
 * Home Page: http://blog.csdn.net/zxt0601
 * Created:   2017/9/27.
 * History:
 */

public class DbOpenHelper extends SQLiteOpenHelper {
    private static final String TAG = "zxt/DbOpenHelper";

    public DbOpenHelper(Context context) {
        super(context, SQConstant.DB_NAME, null, SQConstant.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(TAG, "onCreate() called with: db = [" + db + "]");
        String sql = "create table " + SQConstant.TABLE_ORDER_PRINT
                + " (" + SQConstant.COLUMN_PRIMARY_KEY + " Integer primary key,"
                + SQConstant.COLUMN_ORDER_ID + " varchar(30))";
        db.execSQL(sql);

/*
        String sql = "create table stu_table(id int,sname varchar(20),sage int,ssex varchar(10))";
//输出创建数据库的日志信息
        Log.i(TAG, "create Database------------->");
//execSQL函数用于执行SQL语句
        db.execSQL(sql);*/
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(TAG, "onUpgrade() called with: db = [" + db + "], oldVersion = [" + oldVersion + "], newVersion = [" + newVersion + "]");
    }
}
