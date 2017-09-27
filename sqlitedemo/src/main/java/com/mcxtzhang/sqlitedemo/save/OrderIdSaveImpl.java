package com.mcxtzhang.sqlitedemo.save;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Intro:
 * Author: zhangxutong
 * E-mail: mcxtzhang@163.com
 * Home Page: http://blog.csdn.net/zxt0601
 * Created:   2017/9/27.
 * History:
 */

public enum OrderIdSaveImpl implements IOrderIdSaveManager {
    INSTANCE;

    private DbOpenHelper mDbOpenHelper;

    OrderIdSaveImpl init(Context context) {
        if (mDbOpenHelper == null) {
            mDbOpenHelper = new DbOpenHelper(context);
        }
        return this;
    }

    @Override
    public void saveOrderId(String orderId) {
        SQLiteDatabase db = mDbOpenHelper.getWritableDatabase();
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put(SQConstant.COLUMN_ORDER_ID, orderId);
            db.insert(SQConstant.TABLE_ORDER_PRINT, null, contentValues);
        } finally {
            db.close();
        }
    }

    @Override
    public boolean existOrderId(String orderId) {
        //得到一个可写的数据库
        SQLiteDatabase db = mDbOpenHelper.getReadableDatabase();
//参数1：表名
//参数2：要想显示的列
//参数3：where子句
//参数4：where子句对应的条件值
//参数5：分组方式
//参数6：having条件
//参数7：排序方式
/*        Cursor cursor = db.query("stu_table", new String[]{"id","sname","sage","ssex"}, "id=?", new String[]{"1"}, null, null, null);
        while(cursor.moveToNext()){
            String name = cursor.getString(cursor.getColumnIndex("sname"));
            String age = cursor.getString(cursor.getColumnIndex("sage"));
            String sex = cursor.getString(cursor.getColumnIndex("ssex"));
            System.out.println("query------->" + "姓名："+name+" "+"年龄："+age+" "+"性别："+sex);
        }*/
//关闭数据库
        try {
            Cursor query = db.query(SQConstant.TABLE_ORDER_PRINT, null,
                    SQConstant.COLUMN_ORDER_ID + "=?", new String[]{orderId}, null, null, null);
            if (null != query && query.getCount() > 0) return true;
            return false;
        } finally {
            db.close();
        }
    }
}
