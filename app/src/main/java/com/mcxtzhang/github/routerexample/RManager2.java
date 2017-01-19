package com.mcxtzhang.github.routerexample;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;

/**
 * Intro: routerMap store Path-ComponentName
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
    }

    private static class InnerRManager {
        private static RManager2 INSTANCE = new RManager2();
    }

    public static RManager2 getInstance() {
        return InnerRManager.INSTANCE;
    }

    public void jump(Context context, String where) {
        String clsFullName = routerMap.get(where);
        if (TextUtils.isEmpty(clsFullName)) {
            Log.e(TAG, "Error jump() called with: where = [" + where + "] not found in routerMap!");
        } else {
            Intent intent = new Intent();
            intent.setComponent(new ComponentName(context.getPackageName(), clsFullName));
            context.startActivity(intent);
            Log.d(TAG, "jump success:" + where);
        }
    }
}
