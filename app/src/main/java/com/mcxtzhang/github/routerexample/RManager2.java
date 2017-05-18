package com.mcxtzhang.github.routerexample;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;

/**
 * Intro: routerMap store Path-ComponentName
 * It has a problem: startActivity and startActivityForResult 's codes is copied.
 * It is inconvenient in modification.
 * <p>
 * Author: zhangxutong
 * E-mail: mcxtzhang@163.com
 * Home Page: http://blog.csdn.net/zxt0601
 * Created:   2017/1/18.
 * History:
 */

public class RManager2 {
    private static final String TAG = "zxt/RManager";

    private Map<String, String> routerMap;

    private RManager2() {
        routerMap = new HashMap<>();
        routerMap.put("rx", "com.mcxtzhang.github.RxActivity");
        routerMap.put("firstKotlin","com.mcxtzhang.github.kotlin.FirstKotlinActivity");
    }

    private static class InnerRManager {
        private static RManager2 INSTANCE = new RManager2();
    }

    public static RManager2 getInstance() {
        return InnerRManager.INSTANCE;
    }

    public void jump(Activity activity, String where, Bundle bundle) {
        String clsFullName = routerMap.get(where);
        if (TextUtils.isEmpty(clsFullName)) {
            Log.e(TAG, "Error in jump() where = [" + where + "] not found in routerMap!");
        } else {
            Intent intent = new Intent();
            intent.setComponent(new ComponentName(activity.getPackageName(), clsFullName));
            if (null != bundle) {
                intent.putExtras(bundle);
            }
            activity.startActivity(intent);
            Log.d(TAG, "ump success:" + where);
        }
    }

    public void jump(Activity activity, String where, Bundle bundle, int requestCode) {
        String clsFullName = routerMap.get(where);
        if (TextUtils.isEmpty(clsFullName)) {
            Log.e(TAG, "Error in jump() where = [" + where + "] not found in routerMap!");
        } else {
            Intent intent = new Intent();
            intent.setComponent(new ComponentName(activity.getPackageName(), clsFullName));
            if (null != bundle) {
                intent.putExtras(bundle);
            }
            activity.startActivityForResult(intent, requestCode);
            Log.d(TAG, "ump success:" + where);
        }
    }
}
