package com.mcxtzhang.databindingdemo;

import android.databinding.BindingAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Intro: Some BindingAdapter utils for DataBinding.
 * Author: zhangxutong
 * E-mail: mcxtzhang@163.com
 * Home Page: http://blog.csdn.net/zxt0601
 * Created:   2017/3/10.
 * History:
 */

public class ZxtBindingUtils {

    @BindingAdapter(value = {"showLong2StringFull"}, requireAll = false)
    public static void long2String(TextView tv, long time) {
        tv.setText("时间是:" + getStrDate(time, "yyyy-MM-dd HH:mm:ss"));
    }

    /**
     * 时间戳转换成字符串
     *
     * @param time
     * @param format 转换格式
     * @return
     */
    public static String getStrDate(Long time, String format) {
        String tmpTime = String.valueOf(time);
        if (tmpTime.length() == 10) {
            time = time * 1000;
        }

        String ret = null;
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        ret = sdf.format(new Date(time));

        return ret;
    }

}
