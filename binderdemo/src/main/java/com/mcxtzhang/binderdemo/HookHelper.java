package com.mcxtzhang.binderdemo;

import android.app.Activity;
import android.app.Instrumentation;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 介绍：
 * 作者：zhangxutong
 * 邮箱：mcxtzhang@163.com
 * 主页：http://blog.csdn.net/zxt0601
 * 时间： 2017/10/14.
 */
public class HookHelper {

    public static void hookActivityStartActivity(Activity activity) {
        try {
            //1 拿到instrumentation 对象
            //注意要用Activity.class  因为这个变量在基Activity类里
            Field filedInstrumentation = Activity.class.getDeclaredField("mInstrumentation");
            filedInstrumentation.setAccessible(true);
            Object instrumentation = filedInstrumentation.get(activity);

            //2 构造hook对象
            HookInstrumentation hookInstrumentation = new HookInstrumentation((Instrumentation) instrumentation);


            //3 设置回去
            filedInstrumentation.set(activity, hookInstrumentation);


        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }


    }

    public static void hookApplication() {
        /**
         *         mMainThread.getInstrumentation().execStartActivity(
         getOuterContext(), mMainThread.getApplicationThread(), null,
         (Activity) null, intent, -1, options);
         */


        /**
         *     public static ActivityThread currentActivityThread() {
         return sCurrentActivityThread;
         }
         */

        try {
            //拿到ActivityThread 对象
            Class<?> cActivityThread = Class.forName("android.app.ActivityThread");
            Method currentActivityThread = cActivityThread.getMethod("currentActivityThread");
            currentActivityThread.setAccessible(true);
            Object activityThread = currentActivityThread.invoke(null);

            //拿到其中的Instrumentation对象
            Field mInstrumentation = cActivityThread.getDeclaredField("mInstrumentation");
            mInstrumentation.setAccessible(true);
            Object instrumentation = mInstrumentation.get(activityThread);


            //得到代理的Insteumentation对象
            HookInstrumentation hookInstrumentation = new HookInstrumentation((Instrumentation) instrumentation);
            //替换
            mInstrumentation.set(activityThread, hookInstrumentation);


        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }
}
