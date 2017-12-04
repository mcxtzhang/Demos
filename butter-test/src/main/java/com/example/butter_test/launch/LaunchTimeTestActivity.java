package com.example.butter_test.launch;

import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.example.butter_test.R;

public class LaunchTimeTestActivity extends AppCompatActivity {

    public static final String TAG = "ActivityManager/Test/Displayed";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "LaunchTimeTestActivity onCreate() called with: + System.currentTimeMillis() = [" + +System.currentTimeMillis() + "]");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch_time_test);
        Log.d(TAG, "LaunchTimeTestActivity onCreate() called with: + System.currentTimeMillis() = [" + +System.currentTimeMillis() + "]");
        getWindow().getDecorView().addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View view, int i, int i1, int i2, int i3, int i4, int i5, int i6, int i7) {
                Log.d(TAG, "LaunchTimeTestActivity onLayoutChange() called with: view = [" + view + "], i = [" + i + System.currentTimeMillis());
            }
        });
        getWindow().getDecorView().post(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, "run() called:" + System.currentTimeMillis());
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "LaunchTimeTestActivity onResume() called:" + System.currentTimeMillis());
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        Log.d(TAG, "LaunchTimeTestActivity onWindowFocusChanged() called:" + System.currentTimeMillis());
    }
}
