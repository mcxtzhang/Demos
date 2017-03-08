package com.mcxtzhang;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import java.util.Map;


/**
 * 介绍：
 * 作者：zhangxutong
 * 邮箱：mcxtzhang@163.com
 * 主页：http://blog.csdn.net/zxt0601
 * 时间： 2017/3/9.
 */
public class ZRouterBooter {
    private static final String TAG = "zxt/ZRouterBooter";
    private static Map<String, String> routerMap;

    public static void initRouterRules(){
        if (null==routerMap){
            routerMap = Rules.
        }
    }

    public static void jump(Activity activity, String where, Bundle bundle) {
        String clsFullName = routerMap.get(where);
        if (TextUtils.isEmpty(clsFullName)) {
            Log.e(TAG, "Error in jump() where = [" + where + "] not found in routerMap!");
        }
        else {
            Intent intent = new Intent();
            intent.setComponent(new ComponentName(activity.getPackageName(), clsFullName));
            if (null != bundle) {
                intent.putExtras(bundle);
            }
            activity.startActivity(intent);
            Log.d(TAG, "Jump success:" + where);
        }
    }

    public static void jump(Activity activity, String where, Bundle bundle, int requestCode) {
        String clsFullName = routerMap.get(where);
        if (TextUtils.isEmpty(clsFullName)) {
            Log.e(TAG, "Error in jump() where = [" + where + "] not found in routerMap!");
        }
        else {
            Intent intent = new Intent();
            intent.setComponent(new ComponentName(activity.getPackageName(), clsFullName));
            if (null != bundle) {
                intent.putExtras(bundle);
            }
            activity.startActivityForResult(intent, requestCode);
            Log.d(TAG, "Jump success:" + where);
        }
    }
}
