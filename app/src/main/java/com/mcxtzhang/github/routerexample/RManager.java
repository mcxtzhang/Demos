package com.mcxtzhang.github.routerexample;

import android.content.Context;
import android.content.Intent;
import android.util.Log;


import java.util.HashMap;
import java.util.Map;

/**
 * Intro:
 * Author: zhangxutong
 * E-mail: mcxtzhang@163.com
 * Home Page: http://blog.csdn.net/zxt0601
 * Created:   2017/1/18.
 * History:
 */

@Deprecated
public class RManager {
    private static final String TAG = "zxt/RManager";

    private Map<String, Class> routerMap;

    private RManager() {
        routerMap = new HashMap<>();
        try {
            routerMap.put("router1", Class.forName("com.mcxtzhang.github.MainActivity"));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

/*    private static class InnerRManager {
        private static RManager INSTANCE = new RManager();
    }

    public static RManager getInstance() {
        return InnerRManager.INSTANCE;
    }*/

    public void jump(Context context, String where) {
        Class aClass = routerMap.get(where);
        if (null != aClass) {
            context.startActivity(new Intent(context, aClass));
            Log.d(TAG, "jump success:" + where);
        } else {
            Log.e(TAG, "Error jump() called with: where = [" + where + "] not found!");
        }
    }
}
