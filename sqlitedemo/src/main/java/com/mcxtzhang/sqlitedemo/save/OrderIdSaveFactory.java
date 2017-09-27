package com.mcxtzhang.sqlitedemo.save;

import android.content.Context;

/**
 * Intro:
 * Author: zhangxutong
 * E-mail: mcxtzhang@163.com
 * Home Page: http://blog.csdn.net/zxt0601
 * Created:   2017/9/27.
 * History:
 */

public class OrderIdSaveFactory {
    public static IOrderIdSaveManager getOrderIdSaveManager(Context context) {
        return OrderIdSaveImpl.INSTANCE.init(context);
    }
}
