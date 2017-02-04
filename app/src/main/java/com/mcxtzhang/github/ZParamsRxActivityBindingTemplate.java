package com.mcxtzhang.github;

import android.content.Intent;
import android.widget.Toast;

/**
 * Intro:
 * Author: zhangxutong
 * E-mail: mcxtzhang@163.com
 * Home Page: http://blog.csdn.net/zxt0601
 * Created:   2017/2/4.
 * History:
 */
@Deprecated
public class ZParamsRxActivityBindingTemplate {
    public static void bind(RxActivity activity) {
        Intent intent = activity.getIntent();
        if (null != intent) {
            activity.mWhatString = intent.getStringExtra("key-string");
            Toast.makeText(activity, "mWhatString 接受到参数：" + activity.mWhatString, Toast.LENGTH_SHORT).show();
        }
    }
}
