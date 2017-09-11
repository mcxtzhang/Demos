package com.mcxtzhang.toastdemo;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static com.mcxtzhang.toastdemo.R.id.tv;

/**
 * Intro: 西游食显示价格的Toast
 * Author: zhangxutong
 * E-mail: mcxtzhang@163.com
 * Home Page: http://blog.csdn.net/zxt0601
 * Created:   2017/9/11.
 * History:
 */

public class WestToast extends Toast {
    private static Context mContext;
    private static WestToast sWestToast;
    private TextView mTv;

    public WestToast(Context context) {
        super(context);
        View inflate = LayoutInflater.from(context).inflate(R.layout.west_toast, null);
        mTv = (TextView) inflate.findViewById(tv);
        setView(inflate);
        setGravity(Gravity.BOTTOM | Gravity.RIGHT, 0, 0);

        Class clazz = getClass();
        Method m1 = null;
        try {
            m1 = clazz.getMethod("getWindowParams");
            WindowManager.LayoutParams wmLp = (WindowManager.LayoutParams) m1.invoke(this);
            wmLp.width = WindowManager.LayoutParams.MATCH_PARENT;
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public void showText(String text) {
        mTv.setText(text);
        show();
    }

    public static void create(Context context) {
        mContext = context.getApplicationContext();
        if (sWestToast == null)
            sWestToast = new WestToast(context.getApplicationContext());
    }

    public static void destroy() {
        mContext = null;
        sWestToast = null;
    }

    public static void show(String text) {
        if (sWestToast != null) {
            sWestToast.showText(text);
        }
    }
}
