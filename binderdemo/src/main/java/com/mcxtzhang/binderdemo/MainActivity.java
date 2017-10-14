package com.mcxtzhang.binderdemo;

import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Main2Activity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getApplicationContext().startActivity(intent);
            }
        });


    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);
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
