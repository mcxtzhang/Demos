package com.example.butter_test;

import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.os.MessageQueue;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.example.butter_test.launch.LaunchTimeTestActivity;
import com.example.butter_test.meminfo.MeminfoTestActivity;
import com.example.butter_test.meminfo.badcase.ThreadLeakActivity;

public class LauncherActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate() called with: savedInstanceState = [" + savedInstanceState + "]" + this);
        setContentView(R.layout.activity_launcher);

        findViewById(R.id.btnLaunchTimeMonitor).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LauncherActivity.this, LaunchTimeTestActivity.class));
            }
        });

        findViewById(R.id.btnThreadLeak).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LauncherActivity.this, ThreadLeakActivity.class));
            }
        });

        findViewById(R.id.btnMeminfo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LauncherActivity.this, MeminfoTestActivity.class));
            }
        });

        Looper.myQueue().addIdleHandler(new MessageQueue.IdleHandler() {
            @Override
            public boolean queueIdle() {
                long maxMemory = Runtime.getRuntime().maxMemory();
                long usedMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
                Log.d("LowMemMonitor", "queueIdle() called,maxMemory:" + maxMemory + ", usedMemory:" + usedMemory);
                //idleHandle也有可能频繁调用
                return true;
            }
        });
    }

    private static final String TAG = "Memory";

    @Override
    protected void onStart() {
        super.onStart();
        Log.e(TAG, "onStart() called" + this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e(TAG, "onStop() called" + this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e(TAG, "onDestroy() called" + this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e(TAG, "onResume() called" + this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e(TAG, "onPause() called" + this);
    }
}
